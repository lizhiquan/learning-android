package ca.bcit.le_leveille;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.Objects;

public class ArticleDetailActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Get the article that was selected in the previous activity
        Bundle receivedInfo = getIntent().getExtras();
        assert receivedInfo != null;
        int articleIndex = (Integer) Objects.requireNonNull(receivedInfo.get("position"));

        Log.d(TAG, "Article: " + Article.articles.get(articleIndex).getContent());

        Article article = Article.articles.get(articleIndex);
        try {
            displayArticleDetails(article);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void displayArticleDetails(final Article article) throws ParseException {
        ImageView thumbnailImageView = findViewById(R.id.articleImage);
        Glide.with(this)
                .load(article.getSmallThumbnail())
                .into(thumbnailImageView);

        TextView title = findViewById(R.id.articleTitle);
        title.setText(article.getTitle());

        setAuthor(article);
        setPublishedDate(article);
        setSource(article);

        TextView description = findViewById(R.id.description);
        description.setText(article.getDescription());

        TextView content = findViewById(R.id.articleContent);
        content.setText(article.getContent());

        setUrl(article);
    }

    public void setAuthor(Article article) {
        TextView author = findViewById(R.id.author);
        StringBuilder authorStr = new StringBuilder();
        authorStr.append("By ");
        authorStr.append(article.getAuthor());
        author.setText(authorStr);
    }

    public void setPublishedDate(Article article) throws ParseException {
        TextView publishedAt = findViewById(R.id.publishedAt);
        String date = article.getPublishedAt().toString();

        StringBuilder dateStr = new StringBuilder();
        dateStr.append(date.substring(0, 10));
        dateStr.append(", ");
        dateStr.append(date.substring(11, 23));
        publishedAt.setText(dateStr);
    }

    public void setSource(Article article) {
        TextView source = findViewById(R.id.source);
        StringBuilder sourceStr = new StringBuilder();
        sourceStr.append("Source: ");
        sourceStr.append(article.getSource().getName());
        source.setText(sourceStr);
    }

    public void setUrl(final Article article) {
        TextView url = findViewById(R.id.url);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(" ");
        strBuilder.append(article.getUrl());
        url.setText(strBuilder);
        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = article.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlStr));
                startActivity(intent);
            }
        });
    }
}

