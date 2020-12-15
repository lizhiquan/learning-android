package ca.bcit.czone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPageAdapter pagerAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabBar);
        tabLayout.setupWithViewPager(viewPager);
    }

    class SectionsPageAdapter extends FragmentPagerAdapter {
        private final int[] TAB_TITLES = {R.string.tab_nearby, R.string.tab_info, R.string.tab_notifications};

        public SectionsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MapsFragment();
                case 1:
                    return new InformationFragment();
                case 2:
                    return new NotificationsFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(TAB_TITLES[position]);
        }
    }
}