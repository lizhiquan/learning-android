package ca.bcit.myworld;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CountriesFragment extends Fragment {

    private static final String CONTINENT_PARAM = "continent";

    private String continent;

    public CountriesFragment() {}

    public static CountriesFragment newInstance(String continent) {
        CountriesFragment fragment = new CountriesFragment();
        Bundle args = new Bundle();
        args.putString(CONTINENT_PARAM, continent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            continent = getArguments().getString(CONTINENT_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View v = getView();
        if (v != null) {
            RecyclerView recyclerView = v.findViewById(R.id.countries_recycler);
            Country[] countries = Country.getCountriesByContinent(continent);
            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(countries);
            recyclerView.setAdapter(adapter);
            GridLayoutManager lm = new GridLayoutManager(this.getContext(), 2);
            recyclerView.setLayoutManager(lm);
        }
    }
}