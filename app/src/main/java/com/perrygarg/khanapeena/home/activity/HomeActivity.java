package com.perrygarg.khanapeena.home.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.common.activity.BaseActivity;
import com.perrygarg.khanapeena.common.network.WebConstants;
import com.perrygarg.khanapeena.common.util.DelayAutocompleteTextView;
import com.perrygarg.khanapeena.common.util.UIUtil;
import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.adapter.TrainAutocompleteAdapter;
import com.perrygarg.khanapeena.home.contract.HomeContract;
import com.perrygarg.khanapeena.home.model.TrainRoute;
import com.perrygarg.khanapeena.home.presenter.HomePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends BaseActivity implements HomeContract.View, View.OnClickListener, AdapterView.OnItemSelectedListener {
    DelayAutocompleteTextView train;
    Button proceedBtn;
    AppCompatSpinner mealStations;
    HomePresenter homePresenter;
    TrainAutocompleteAdapter adapter;
    ProgressBar trainProgress;
    ProgressDialog dialog;
    ArrayAdapter aa;
    EditText datePickerText;
    Calendar myCalendar = Calendar.getInstance();

    private String selectedTrainNumber;
    private String selectedStationCode;
    private String selectedDate;

    ArrayList<String> availableStationCodes = new ArrayList<>();

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
        train.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Train train1 = (Train) adapterView.getItemAtPosition(position);
                train.setText(train1.trainName);
                adapter.itemSelected(train1);

                selectedTrainNumber = train1.trainNumber;

                fetchRouteOfSelectedTrain(train1);
            }
        });


        fetchServingStationsFromFirebase();
    }

    private void fetchRouteOfSelectedTrain(Train train1) {
        homePresenter.fetchRouteOfSelectedTrain(train1);
    }

    private void fetchServingStationsFromFirebase() {
        homePresenter.fetchServingStations();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void init() {
        findViewsByIds();
        setListeners();

        availableStationCodes.add(getString(R.string.select_delivery_station));

        //Creating the ArrayAdapter instance having the availableStationCodes list
        aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, availableStationCodes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        mealStations.setAdapter(aa);
    }

    private void setListeners() {
        datePickerText.setOnClickListener(this);
        mealStations.setOnItemSelectedListener(this);
        proceedBtn.setOnClickListener(this);
    }

    private void findViewsByIds() {
        train = (DelayAutocompleteTextView) findViewById(R.id.train);
        mealStations = (AppCompatSpinner) findViewById(R.id.meal_stations);
        trainProgress = (ProgressBar) findViewById(R.id.train_progress_bar);
        datePickerText = (EditText) findViewById(R.id.date_picker_edit_text);
        proceedBtn = (Button) findViewById(R.id.proceed_button);
    }

    @Override
    public void showProgress(int processCode) {
        switch (processCode) {
            case WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE:
                trainProgress.setVisibility(View.VISIBLE);
                break;
            case WebConstants.FETCH_SERVING_STATIONS_SERVICE:
                dialog = UIUtil.showProgressDialog(this, getString(R.string.progress_title), getString(R.string.progress_message), true, false);
                break;
            case WebConstants.FETCH_TRAIN_ROUTE_SERVICE:
                dialog = UIUtil.showProgressDialog(this, getString(R.string.progress_title), getString(R.string.available_stations_message), true, false);
                break;
        }
    }

    @Override
    public void hideProgress(int processCode) {
        switch (processCode) {
            case WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE:
                trainProgress.setVisibility(View.INVISIBLE);
                break;
            case WebConstants.FETCH_SERVING_STATIONS_SERVICE:
            case WebConstants.FETCH_TRAIN_ROUTE_SERVICE:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void onAutocompleteTrainSuccess(ArrayList<Train> trainArrayList) {
        adapter.setTrainsListInstance(trainArrayList);
        if(trainArrayList.isEmpty()){
            train.dismissDropDown();
        } else {
            train.showDropDown();
        }
    }

    @Override
    public void fetchAutoCompleteTrainList(final String partialTrainInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homePresenter.fetchAutoCompleteTrainList(partialTrainInfo);
            }
        });
    }

    @Override
    public void setIntersectionStations(ArrayList<TrainRoute> intersectionStations) {
        availableStationCodes.removeAll(availableStationCodes);
        for (TrainRoute route : intersectionStations) {
            availableStationCodes.add(route.stationFullName);
        }
        availableStationCodes.add(0, getString(R.string.select_delivery_station));
        aa.notifyDataSetChanged();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        datePickerText.setText("Journey Date: " + sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_picker_edit_text:
                new DatePickerDialog(this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.proceed_button:

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(getApplicationContext(),country[position] ,Toast.LENGTH_LONG).show();
        //perry
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
