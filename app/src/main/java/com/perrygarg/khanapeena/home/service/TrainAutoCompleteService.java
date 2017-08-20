package com.perrygarg.khanapeena.home.service;

import android.util.Log;

import com.android.volley.Request;
import com.perrygarg.khanapeena.application.MyApplication;
import com.perrygarg.khanapeena.common.network.AppHttpClient;
import com.perrygarg.khanapeena.common.network.WebConstants;
import com.perrygarg.khanapeena.common.network.WebManager;
import com.perrygarg.khanapeena.common.network.WebService;
import com.perrygarg.khanapeena.common.network.WebServiceListener;
import com.perrygarg.khanapeena.home.model.TrainAutoCompleteResponse;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class TrainAutoCompleteService extends WebService {

    public TrainAutoCompleteService(int taskCode, WebServiceListener serviceListener) {
        super(taskCode, serviceListener);
    }

    @Override
    public void getData(Object... args) {
        String request = (String) args[0];

        AppHttpClient httpClient = new AppHttpClient(MyApplication.appContext);
        httpClient.sendGSONRequest(Request.Method.GET, WebConstants.URL_TRAIN_AUTOCOMPLETE + request + WebConstants.RAIL_API_KEY, null, TrainAutoCompleteResponse.class, WebManager.getHeaders(), this, this, tag);
    }

    @Override
    public void onResponse(Object object)
    {
        Log.d("","");
    }

}
