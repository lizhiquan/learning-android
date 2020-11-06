package ca.bcit.myworld;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    private Country[] countries;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public CaptionedImagesAdapter(Country[] countries) {
        this.countries = countries;
    }

    @Override
    public int getItemCount() {
        return countries.length;
    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        Country country = countries[position];

        ImageView imageView = cardView.findViewById(R.id.item_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(),
                country.getImageResourceId());
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(country.getName());

        TextView textView = cardView.findViewById(R.id.item_text);
        textView.setText(country.getName());

        cardView.setOnClickListener(view -> {
            Intent i = new Intent(cardView.getContext(), CountryDetailActivity.class);
            i.putExtra(CountryDetailActivity.COUNTRY_NAME_KEY, country.getName());
            cardView.getContext().startActivity(i);
        });
    }
}

