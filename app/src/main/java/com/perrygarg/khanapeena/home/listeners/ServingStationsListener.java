package com.perrygarg.khanapeena.home.listeners;

import java.util.ArrayList;

/**
 * Created by PerryGarg on 27-08-2017.
 */

public interface ServingStationsListener {

    void onSuccessFetchServingStations(ArrayList<String> stationCodes, int serviceCode);
    void onFailureFetchServingStations(int errCode, int serviceCode, String errMsg);

}
