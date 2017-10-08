package com.perrygarg.khanapeena.home.listeners;

import com.perrygarg.khanapeena.home.model.Train;

import java.util.ArrayList;

/**
 * Created by PerryGarg on 27-08-2017.
 */

public interface ServingStationsListener {

    void onSuccessFetchServingStations(ArrayList<String> stationCodes, int serviceCode);
    void onFailureFetchServingStations(int errCode, int serviceCode, String errMsg);

    void onSuccessFetchTrainList(ArrayList<Train> trainList, int serviceCode);

    void onFailureFetchTrainList(int code, int serviceCode, String message);
}
