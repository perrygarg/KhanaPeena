package com.perrygarg.khanapeena.common.network;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.perrygarg.khanapeena.home.listeners.ServingStationsListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PerryGarg on 27-08-2017.
 */

public class FirebaseValueEventListener implements ValueEventListener {
    ServingStationsListener servingStationsListener;
    int serviceCode;

    public FirebaseValueEventListener(ServingStationsListener listener, int serviceCode) {
        this.servingStationsListener = listener;
        this.serviceCode = serviceCode;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        switch (serviceCode) {
            case WebConstants.FETCH_SERVING_STATIONS_SERVICE:
                ArrayList<String> stationCodes = (ArrayList<String>) dataSnapshot.getValue();
                servingStationsListener.onSuccessFetchServingStations(stationCodes, serviceCode);
                break;
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        switch (serviceCode) {
            case WebConstants.FETCH_SERVING_STATIONS_SERVICE:
                servingStationsListener.onFailureFetchServingStations(databaseError.getCode(), serviceCode, databaseError.getMessage());
                break;
        }
    }
}
