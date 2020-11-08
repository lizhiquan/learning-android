package ca.bcit.le_leveille;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ArticlesAdapter extends ArrayAdapter<Article> {

    public ArticlesAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.article_row, parent, false);
        }

        Article article = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView subtitleTextView = convertView.findViewById(R.id.subtitleTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        ImageView thumbnailImageView = convertView.findViewById(R.id.thumbImage);

        titleTextView.setText(article.getTitle());
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CANADA);
        String publishedAt = format.format(article.getPublishedAt());
        subtitleTextView.setText(String.format("%s - %s", article.getAuthor(), publishedAt));
        descriptionTextView.setText(article.getDescription());
        Glide.with(getContext())
                .load(article.getSmallThumbnail())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(thumbnailImageView);

        return convertView;
    }
}
