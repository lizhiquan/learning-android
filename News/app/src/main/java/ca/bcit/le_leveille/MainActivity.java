package ca.bcit.le_leveille;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title);

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearch(v);
            }
        });

        EditText searchBar = findViewById(R.id.keywordEditText);
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        handleSearch(v);
                        return true;
                    }
                }
                return false;
            }
        });

    }

    public void handleSearch(View view) {
        EditText keywordEditText = findViewById(R.id.keywordEditText);
        String keyword = keywordEditText.getText().toString();
        new GetArticlesTask(keyword).execute();
    }

    private class GetArticlesTask extends AsyncTask<Void, Void, Void> {

        private String serviceUrl;

        GetArticlesTask(String keyword) {
            String fromDate = LocalDate.now()
                    .minusDays(7)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            serviceUrl = Uri.parse("https://newsapi.org/v2/everything")
                    .buildUpon()
                    .appendQueryParameter("q", keyword)
                    .appendQueryParameter("from", fromDate)
                    .appendQueryParameter("sortBy", "publishedAt")
                    .appendQueryParameter("apiKey", "d9a5bdab29f24493a2221aeb919a7d4b")
                    .build()
                    .toString();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();
            String jsonStr;

            // Making a request to url and getting response
            jsonStr = httpHandler.makeServiceCall(serviceUrl);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                Log.d(TAG, "Json: " + jsonStr);
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .create();
                Response response = gson.fromJson(jsonStr, Response.class);
                articles = response.getArticles();
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Article.articles = articles;
            Intent intent = new Intent(getApplicationContext(), ArticlesActivity.class);
            startActivity(intent);
        }
    }
}