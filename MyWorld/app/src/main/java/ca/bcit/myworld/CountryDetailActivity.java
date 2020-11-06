package ca.bcit.myworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryDetailActivity extends AppCompatActivity {

    static final String COUNTRY_NAME_KEY = "country_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView countryName = findViewById(R.id.country_name);
        TextView countryContinent = findViewById(R.id.country_continent);
        TextView countryDescription = findViewById(R.id.country_description);
        ImageView countryImage = findViewById(R.id.country_image);

        Country country = Country.getCountryByName(getIntent().getStringExtra(COUNTRY_NAME_KEY));
        countryName.setText(country.getName());
        countryContinent.setText(country.getContinent());
        countryDescription.setText(country.getDescription());
        countryImage.setImageResource(country.getImageResourceId());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}