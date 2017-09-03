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
import com.perrygarg.khanapeena.home.contract.HomeContract;
import com.perrygarg.khanapeena.home.listeners.ServingStationsListener;
import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.model.TrainAutoCompleteResponse;
import com.perrygarg.khanapeena.home.model.TrainRoute;
import com.perrygarg.khanapeena.home.model.TrainRouteResponse;

import java.util.ArrayList;
import java.util.Arrays;

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
                routes = new ArrayList<>(Arrays.asList(route));
                calculateIntersectionStations(servingStationCodes, routes);
                break;
        }

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
