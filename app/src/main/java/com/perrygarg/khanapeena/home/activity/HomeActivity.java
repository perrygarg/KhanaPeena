package com.perrygarg.khanapeena.home.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.common.activity.BaseActivity;
import com.perrygarg.khanapeena.common.network.WebConstants;
import com.perrygarg.khanapeena.common.util.DelayAutocompleteTextView;
import com.perrygarg.khanapeena.common.util.UIUtil;
import com.perrygarg.khanapeena.home.adapter.TrainAutocompleteAdapter;
import com.perrygarg.khanapeena.home.contract.HomeContract;
import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.model.TrainRoute;
import com.perrygarg.khanapeena.home.presenter.HomePresenter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

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

    ArrayList<String> availableStationNames = new ArrayList<>();
    ArrayList<TrainRoute> intersectionStations = new ArrayList<>();
    private Calendar[] disabledDates;

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

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void init() {
        findViewsByIds();
        setListeners();

        availableStationNames.add(getString(R.string.select_delivery_station));

        //Creating the ArrayAdapter instance having the availableStationNames list
        aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, availableStationNames);
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
        train = findViewById(R.id.train);
        mealStations = findViewById(R.id.meal_stations);
        trainProgress = findViewById(R.id.train_progress_bar);
        datePickerText = findViewById(R.id.date_picker_edit_text);
        proceedBtn = findViewById(R.id.proceed_button);
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
            case WebConstants.CHECK_TRAIN_LIVE_API_SERVICE:
                dialog = UIUtil.showProgressDialog(this, getString(R.string.progress_title), getString(R.string.live_api_check_message), true, false);
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
            case WebConstants.CHECK_TRAIN_LIVE_API_SERVICE:
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
        this.intersectionStations = intersectionStations;
        availableStationNames.removeAll(availableStationNames);
        for (TrainRoute route : intersectionStations) {
            availableStationNames.add(route.stationFullName);
        }
        availableStationNames.add(0, getString(R.string.select_delivery_station));
        aa.notifyDataSetChanged();
    }

    @Override
    public void disableDates(Calendar[] disabledDates) {
        this.disabledDates = disabledDates;
    }

    @Override
    public void showToastMessage(int taskCode) {
        UIUtil.showToast("Live API didn't work");
    }

    @Override
    public void onSuccessCheckTrainRunAheadViaLiveAPI(boolean shouldProceed) {
        if(shouldProceed) {
            goToNextScreen();
        } else {
            showError(getString(R.string.order_impossible_error));
        }
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        datePickerText.setText("Journey Date: " + sdf.format(myCalendar.getTime()));
        selectedDate = sdf.format(myCalendar.getTime());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_picker_edit_text:
                myCalendar = Calendar.getInstance();
                DatePickerDialog pickerDialog = DatePickerDialog.newInstance(dateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);
                pickerDialog.show(getFragmentManager(), "JourneyDateSelector");
                pickerDialog.setMinDate(myCalendar);
                myCalendar.add(Calendar.MONTH, 1); //adding a month to the calendar
                pickerDialog.setMaxDate(myCalendar);
                myCalendar.add(Calendar.MONTH, -1); //subtracting a month from the calendar
                pickerDialog.setDisabledDays(disabledDates);
                break;

            case R.id.proceed_button:
                clickOnProceedButton();
                break;
        }
    }

    private void clickOnProceedButton() {
        if(!selectedTrainNumber.isEmpty() && !selectedStationCode.isEmpty() && !selectedDate.isEmpty()) {
            if(selectedDateLiesInFuture(selectedDate)) {
                goToNextScreen();
            } else {
                checkTrainRunAheadViaLiveAPI(selectedTrainNumber, selectedDate, selectedStationCode);
            }
        }
    }

    private void goToNextScreen() {
        UIUtil.showToast("Next Screen");
    }

    private boolean selectedDateLiesInFuture(String selectedDate) {
        Calendar calendar = Calendar.getInstance();
        return myCalendar.after(calendar);
    }

    private void checkTrainRunAheadViaLiveAPI(String selectedTrainNumber, String selectedDate, String selectedStationCode) {
        homePresenter.checkTrainRunAheadViaLiveAPI(selectedTrainNumber, selectedDate, selectedStationCode);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i != 0) {
            for(TrainRoute route : intersectionStations) {
                if(route.stationFullName.equals(availableStationNames.get(i))) {
                    selectedStationCode = route.stationCode;
                    break;
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
