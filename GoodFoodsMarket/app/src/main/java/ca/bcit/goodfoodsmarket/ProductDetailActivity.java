package ca.bcit.goodfoodsmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailActivity extends AppCompatActivity {

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        int position = intent.getIntExtra("position", 0);
        Food food = Food.getFoodFromCategory(category)[position];

        ImageView foodImageView = findViewById(R.id.foodImageView);
        foodImageView.setImageResource(food.getImageResourceId());

        TextView nameTextView = findViewById(R.id.nameTextView);
        nameTextView.setText(food.getName());

        TextView categoryTextView = findViewById(R.id.categoryTextView);
        categoryTextView.setText("Category: " + food.getCategory());

        TextView countryTextView = findViewById(R.id.countryTextView);
        countryTextView.setText("Country of origin: " + food.getCountryOfOrigin());

        TextView unitTextView = findViewById(R.id.unitTextView);
        unitTextView.setText("Unit: " + food.getUnit());

        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText(String.format("Price: %.2f", food.getPrice()));
    }
}