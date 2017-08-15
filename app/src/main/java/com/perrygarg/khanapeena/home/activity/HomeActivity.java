package com.perrygarg.khanapeena.home.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.common.activity.BaseActivity;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {
    AutoCompleteTextView train;
    AutoCompleteTextView station;
    String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
    ArrayList<String> languageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar(getString(R.string.app_name), false);

        init();

        languageList.add("perry");
        languageList.add("perry");
        languageList.add("perry");
        languageList.add("perry");
        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, languageList);
        //Getting the instance of AutoCompleteTextView
        train.setThreshold(3);//will start working from first character
        train.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

    private void init() {
        findViewsByIds();
    }

    private void findViewsByIds() {
        train = (AutoCompleteTextView) findViewById(R.id.train);
        station = (AutoCompleteTextView) findViewById(R.id.meal_station);
    }
}
