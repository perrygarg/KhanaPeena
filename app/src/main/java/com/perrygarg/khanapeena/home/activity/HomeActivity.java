package com.perrygarg.khanapeena.home.activity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.common.activity.BaseActivity;

public class HomeActivity extends BaseActivity {
    AutoCompleteTextView train;
    AutoCompleteTextView station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar(getString(R.string.app_name), false);

        init();
    }

    private void init() {
        findViewsByIds();
    }

    private void findViewsByIds() {
        train = (AutoCompleteTextView) findViewById(R.id.train);
        station = (AutoCompleteTextView) findViewById(R.id.meal_station);
    }
}
