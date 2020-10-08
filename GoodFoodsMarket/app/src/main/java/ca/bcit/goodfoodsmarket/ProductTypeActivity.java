package ca.bcit.goodfoodsmarket;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProductTypeActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String category = getIntent().getStringExtra("category");
        Food[] foods = Food.getFoodFromCategory(category);

        ArrayAdapter<Food> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, foods
        );
        ListView foodsListView = getListView();
        foodsListView.setAdapter(arrayAdapter);
        foodsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Food food = (Food) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                intent.putExtra("category", food.getCategory());
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}