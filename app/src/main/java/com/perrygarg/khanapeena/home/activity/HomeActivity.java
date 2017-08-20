package com.perrygarg.khanapeena.home.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar(getString(R.string.app_name), false);

        init();

        homePresenter = new HomePresenter(this);

        train.setThreshold(3);
        final TrainAutocompleteAdapter adapter = new TrainAutocompleteAdapter(this, this);
        train.setAdapter(adapter);
//        bookTitle.setLoadingIndicator(
//                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));
        train.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Train train1 = (Train) adapterView.getItemAtPosition(position);
                train.setText(train1.trainName);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Train> trains = new ArrayList<>();
                Train train = new Train();
                train.trainName = "dummy1";
                train.trainNumber = "12345";
                trains.add(train);
                Train train1 = new Train();
                train1.trainName = "dummy2";
                train1.trainNumber = "23456";
                trains.add(train1);
adapter.setTrainsListInstance(trains);
            }
        }, 5000);

    }

    private void init() {
        findViewsByIds();
    }

    private void findViewsByIds() {
        train = (DelayAutocompleteTextView) findViewById(R.id.train);
        station = (AutoCompleteTextView) findViewById(R.id.meal_station);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void onAutocompleteTrainSuccess() {

    }

    @Override
    public void fetchAutoCompleteTrainList(String partialTrainInfo) {
        homePresenter.fetchAutoCompleteTrainList(partialTrainInfo);
    }
}
