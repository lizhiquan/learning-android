package ca.bcit.le_leveille;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArticlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        ArticlesAdapter adapter = new ArticlesAdapter(this, Article.articles);
        ListView articlesListView = findViewById(R.id.articlesListView);
        articlesListView.setAdapter(adapter);
        articlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ArticleDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}