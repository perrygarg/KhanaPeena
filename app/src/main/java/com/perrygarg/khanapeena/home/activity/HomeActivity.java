package com.perrygarg.khanapeena.home.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.common.activity.BaseActivity;
import com.perrygarg.khanapeena.common.util.DelayAutocompleteTextView;
import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.adapter.TrainAutocompleteAdapter;
import com.perrygarg.khanapeena.home.contract.HomeContract;
import com.perrygarg.khanapeena.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeContract.View {
    DelayAutocompleteTextView train;
    AutoCompleteTextView station;
    HomePresenter homePresenter;
    TrainAutocompleteAdapter adapter;
    ProgressBar trainProgress, mealStationProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar(getString(R.string.app_name), false);

        init();

        homePresenter = new HomePresenter(this);

        train.setThreshold(3);
        adapter = new TrainAutocompleteAdapter(this, this);
        train.setAdapter(adapter);
//        bookTitle.setLoadingIndicator(
//                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));
        train.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                adapter.itemSelected(true);
                Train train1 = (Train) adapterView.getItemAtPosition(position);
                train.setText(train1.trainName);
            }
        });

//        adapter.setTrainsListInstance(trains);

    }

    private void init() {
        findViewsByIds();
    }

    private void findViewsByIds() {
        train = (DelayAutocompleteTextView) findViewById(R.id.train);
        station = (AutoCompleteTextView) findViewById(R.id.meal_station);
        trainProgress = (ProgressBar) findViewById(R.id.train_progress_bar);
        mealStationProgress = (ProgressBar) findViewById(R.id.meal_station_progress_bar);
    }

    @Override
    public void showProgress() {
        trainProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        trainProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void onAutocompleteTrainSuccess(ArrayList<Train> trainArrayList) {
        adapter.setTrainsListInstance(trainArrayList);
        train.showDropDown();
    }

    @Override
    public void fetchAutoCompleteTrainList(String partialTrainInfo) {
        homePresenter.fetchAutoCompleteTrainList(partialTrainInfo);
    }
}
