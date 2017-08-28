package com.perrygarg.khanapeena.home.presenter;

import com.perrygarg.khanapeena.common.model.MasterResponse;
import com.perrygarg.khanapeena.common.model.ValidationError;
import com.perrygarg.khanapeena.common.network.FirebaseNetworkManager;
import com.perrygarg.khanapeena.common.network.FirebaseValueEventListener;
import com.perrygarg.khanapeena.common.network.WebConstants;
import com.perrygarg.khanapeena.common.network.WebManager;
import com.perrygarg.khanapeena.common.network.WebService;
import com.perrygarg.khanapeena.common.network.WebServiceListener;
import com.perrygarg.khanapeena.common.util.AppLogs;
import com.perrygarg.khanapeena.home.contract.HomeContract;
import com.perrygarg.khanapeena.home.listeners.ServingStationsListener;
import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.model.TrainAutoCompleteResponse;

import java.util.ArrayList;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class HomePresenter implements HomeContract.Presenter, WebServiceListener, ServingStationsListener {
    HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchAutoCompleteTrainList(String partialTrainInfo) {
        view.showProgress();
        WebService service = WebManager.getService(WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE, this);
        service.getData(partialTrainInfo);
    }

    @Override
    public void fetchServingStations() {
        FirebaseValueEventListener listener = new FirebaseValueEventListener(this, WebConstants.FETCH_SERVING_STATIONS_SERVICE);
        FirebaseNetworkManager.getInstance().fetchServingStations(listener);
    }

    @Override
    public void onServiceBegin(int taskCode) {

    }

    @Override
    public void onServiceSuccess(MasterResponse masterResponse, int taskCode) {
        Train[] trains = ((TrainAutoCompleteResponse)masterResponse).train;
        int totalTrains = ((TrainAutoCompleteResponse)masterResponse).total;
        if(totalTrains > 5) {
//            totalTrains = 5;
        }
        ArrayList<Train> trainList = new ArrayList<>();
        for (int i = 0; i < totalTrains; i++) {
            trainList.add(trains[i]);
        }
        view.hideProgress();
        view.onAutocompleteTrainSuccess(trainList);
    }

    @Override
    public void onServiceError(String message, int taskCode, int errorType) {

    }

    @Override
    public void onValidationError(ValidationError[] validationError, int taskCode) {

    }

    @Override
    public void onSuccessFetchServingStations(ArrayList<String> stationCodes, int serviceCode) {

    }

    @Override
    public void onFailureFetchServingStations(int errCode, int serviceCode, String errMsg) {

    }
}
