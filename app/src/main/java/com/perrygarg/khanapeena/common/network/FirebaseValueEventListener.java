package com.perrygarg.khanapeena.common.network;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.perrygarg.khanapeena.home.listeners.ServingStationsListener;
import com.perrygarg.khanapeena.home.model.AppConfig;
import com.perrygarg.khanapeena.home.model.Train;

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
            case WebConstants.FETCH_CONFIG_SERVICE:
                AppConfig config = dataSnapshot.getValue(AppConfig.class);
                servingStationsListener.onSuccessFetchConfig(config, serviceCode);
                break;
            case WebConstants.FETCH_TRAIN_LIST_SERVICE:
                ArrayList<HashMap> list = (ArrayList<HashMap>) dataSnapshot.getValue();
                ArrayList<Train> trainList = new ArrayList<>();
                for (HashMap map : list) {
                    Train train = new Train();
                    train.name = (String) map.get("name");
                    train.number = Long.toString((Long) map.get("number"));
                    train.sourcestn = (String) map.get("sourcestn");
                    train.destinationstn = (String) map.get("destinationstn");
                    trainList.add(train);
                }
                servingStationsListener.onSuccessFetchTrainList(trainList, serviceCode);
                break;
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        switch (serviceCode) {
            case WebConstants.FETCH_CONFIG_SERVICE:
                servingStationsListener.onFailureFetchServingStations(databaseError.getCode(), serviceCode, databaseError.getMessage());
                break;
            case WebConstants.FETCH_TRAIN_LIST_SERVICE:
                servingStationsListener.onFailureFetchTrainList(databaseError.getCode(), serviceCode, databaseError.getMessage());
                break;
        }
    }
}
