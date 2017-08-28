package com.perrygarg.khanapeena.common.network;

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
//                HashMap hashMap1 = (HashMap) dataSnapshot.getValue();
//                if (hashMap1 != null) {
//                    for (Object entry : hashMap1.entrySet()) {
//                        HashMap hashMap2 = (HashMap) ((Map.Entry) entry).getValue();
//                        if (hashMap2.get(AppConstants.RELATION_TYPE).equals(AppConstants.PARENT_OF)) {
//                            ids.add((String) hashMap2.get(AppConstants.USER2_ID));
//                        }
//                    }
//                }
//
//                networkListener.onSuccessFetchingCrIdList(AppConstants.GET_CR_ID_LIST_SERVICE, ids);
                break;
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
