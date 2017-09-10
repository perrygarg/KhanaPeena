package com.perrygarg.khanapeena.home.presenter;

import android.util.Log;

import com.perrygarg.khanapeena.common.model.MasterResponse;
import com.perrygarg.khanapeena.common.model.ValidationError;
import com.perrygarg.khanapeena.common.network.FirebaseNetworkManager;
import com.perrygarg.khanapeena.common.network.FirebaseValueEventListener;
import com.perrygarg.khanapeena.common.network.WebConstants;
import com.perrygarg.khanapeena.common.network.WebManager;
import com.perrygarg.khanapeena.common.network.WebService;
import com.perrygarg.khanapeena.common.network.WebServiceListener;
import com.perrygarg.khanapeena.common.util.AppUtil;
import com.perrygarg.khanapeena.home.contract.HomeContract;
import com.perrygarg.khanapeena.home.listeners.ServingStationsListener;
import com.perrygarg.khanapeena.home.model.Day;
import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.model.TrainAutoCompleteResponse;
import com.perrygarg.khanapeena.home.model.TrainDays;
import com.perrygarg.khanapeena.home.model.TrainRoute;
import com.perrygarg.khanapeena.home.model.TrainRouteResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class HomePresenter implements HomeContract.Presenter, WebServiceListener, ServingStationsListener {
    HomeContract.View view;
    private ArrayList<String> servingStationCodes;
    private ArrayList<TrainRoute> routes;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchAutoCompleteTrainList(String partialTrainInfo) {
        view.showProgress(WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE);
        WebService service = WebManager.getService(WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE, this);
        service.getData(partialTrainInfo);
    }

    @Override
    public void fetchRouteOfSelectedTrain(Train train) {
        view.showProgress(WebConstants.FETCH_TRAIN_ROUTE_SERVICE);
        WebService service = WebManager.getService(WebConstants.FETCH_TRAIN_ROUTE_SERVICE, this);
        service.getData(train);
    }

    @Override
    public void fetchServingStations() {
        view.showProgress(WebConstants.FETCH_SERVING_STATIONS_SERVICE);
        FirebaseValueEventListener listener = new FirebaseValueEventListener(this, WebConstants.FETCH_SERVING_STATIONS_SERVICE);
        FirebaseNetworkManager.getInstance().fetchServingStations(listener);
    }

    @Override
    public void onServiceBegin(int taskCode) {

    }

    @Override
    public void onServiceSuccess(MasterResponse masterResponse, int taskCode) {
        switch (taskCode) {
            case WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE:
                Train[] trains = ((TrainAutoCompleteResponse)masterResponse).train;
                int totalTrains = ((TrainAutoCompleteResponse)masterResponse).total;
                if(totalTrains > 5) {
//            totalTrains = 5;
                }
                ArrayList<Train> trainList = new ArrayList<>();
                for (int i = 0; i < totalTrains; i++) {
                    trainList.add(trains[i]);
                }
                view.hideProgress(WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE);
                view.onAutocompleteTrainSuccess(trainList);
                break;

            case WebConstants.FETCH_TRAIN_ROUTE_SERVICE:
                TrainRoute[] route = ((TrainRouteResponse)masterResponse).trainRoute;
                TrainDays trainDays = ((TrainRouteResponse)masterResponse).trainDays;
                routes = new ArrayList<>(Arrays.asList(route));
                disableNotRunningDatesInDatePicker(trainDays);
                calculateIntersectionStations(servingStationCodes, routes);
                break;
        }

    }

    private void disableNotRunningDatesInDatePicker(TrainDays trainDays) {
        Day[] days = trainDays.day;

        ArrayList<String> notRunningDays = new ArrayList<>();

        for(Day day : days) {
            if(day.runs.equals("N")) {
                notRunningDays.add(day.dayCode);
            }
        }

        ArrayList<Calendar> invalidCalendars = new ArrayList<>();

        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.add(Calendar.MONTH, 1);
        int fromMonth = fromCalendar.get(Calendar.MONTH);
        int toMonth = toCalendar.get(Calendar.MONTH);
        for(int i = fromMonth; i <= toMonth; i++) {
            int totalDays;
            int startDate = 1;
            if(i == fromMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(fromCalendar.get(Calendar.YEAR), i, fromCalendar.get(Calendar.DATE));
                totalDays = cal.getActualMaximum(Calendar.DATE);
                startDate = cal.get(Calendar.DATE);
            } else if(i == toMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(toCalendar.get(Calendar.YEAR), i, toCalendar.get(Calendar.DATE));
                totalDays = cal.get(Calendar.DATE);
                startDate = 1;
            } else {
                Calendar cal = Calendar.getInstance();
                cal.set(fromCalendar.get(Calendar.YEAR), i, 1);
                totalDays = cal.getActualMaximum(Calendar.DATE);
            }

            for (int ii = startDate; ii <= totalDays; ii++) {
                Calendar tempCal = Calendar.getInstance();
                tempCal.set(toCalendar.get(Calendar.YEAR), i, ii);
                if(notRunningDays.contains(AppUtil.getDayOfWeek(tempCal.get(Calendar.DAY_OF_WEEK)))) {
                    invalidCalendars.add(tempCal);
                }
            }
        }

        Calendar[] disabledDates = new Calendar[invalidCalendars.size()];
        disabledDates = invalidCalendars.toArray(disabledDates);

        view.disableDates(disabledDates);
    }

    private void calculateIntersectionStations(ArrayList<String> servingStationCodes, ArrayList<TrainRoute> routes) {
        ArrayList<TrainRoute> intersectionStations = new ArrayList<>();
        if(servingStationCodes != null && routes != null) {
            for (TrainRoute route : routes) {
                if(servingStationCodes.contains(route.stationCode)) {
                    intersectionStations.add(route);
                }
            }
            view.setIntersectionStations(intersectionStations);
            view.hideProgress(WebConstants.FETCH_TRAIN_ROUTE_SERVICE);
        }
    }

    @Override
    public void onServiceError(String message, int taskCode, int errorType) {

    }

    @Override
    public void onValidationError(ValidationError[] validationError, int taskCode) {

    }

    @Override
    public void onSuccessFetchServingStations(ArrayList<String> stationCodes, int serviceCode) {
        view.hideProgress(serviceCode);
        this.servingStationCodes = stationCodes;
    }

    @Override
    public void onFailureFetchServingStations(int errCode, int serviceCode, String errMsg) {

    }
}
