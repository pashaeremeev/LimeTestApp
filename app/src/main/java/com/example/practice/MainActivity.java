package com.example.practice;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private static final int SIZE_OF_SEARCH_TEXT_PX = 46;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private final String[] tabNames = new String[]{"Все", "Избранные"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.pager);

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(tabNames[position])).attach();

        toSetSettingsOfSearchView();
    }

    private void toSetSettingsOfSearchView() {
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.searchText,getTheme()));
        searchEditText.setHintTextColor(getResources().getColor(R.color.searchText, getTheme()));
        searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, SIZE_OF_SEARCH_TEXT_PX);

        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        searchIcon.setColorFilter(getResources().getColor(R.color.searchText, getTheme()),
                android.graphics.PorterDuff.Mode.SRC_IN);

    }
}