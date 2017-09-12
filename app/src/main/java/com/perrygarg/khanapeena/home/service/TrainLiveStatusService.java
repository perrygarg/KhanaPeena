package com.perrygarg.khanapeena.home.service;

import com.android.volley.Request;
import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.application.MyApplication;
import com.perrygarg.khanapeena.common.network.AppHttpClient;
import com.perrygarg.khanapeena.common.network.WebConstants;
import com.perrygarg.khanapeena.common.network.WebManager;
import com.perrygarg.khanapeena.common.network.WebService;
import com.perrygarg.khanapeena.common.network.WebServiceListener;
import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.model.TrainLiveStatusResponse;
import com.perrygarg.khanapeena.home.model.TrainRouteResponse;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class TrainLiveStatusService extends WebService {

    public TrainLiveStatusService(int taskCode, WebServiceListener serviceListener) {
        super(taskCode, serviceListener);
    }

    @Override
    public void getData(Object... args) {
        String trainNumber = (String) args[0];
        String date = (String) args[1];

        AppHttpClient httpClient = new AppHttpClient(MyApplication.appContext);
        httpClient.sendGSONRequest(Request.Method.GET, WebConstants.URL_TRAIN_LIVE_STATUS + trainNumber + WebConstants.DATE + date + WebConstants.RAIL_API_KEY, null, TrainLiveStatusResponse.class, WebManager.getHeaders(), this, this, tag);
    }

    @Override
    public void onResponse(Object object)
    {
        TrainLiveStatusResponse response = (TrainLiveStatusResponse) object;
        if(response != null) {
            if(response.responseCode != 200) { //check indicating if Live API worked or not
                serviceListener.onServiceError(MyApplication.appContext.getString(R.string.live_api_error), WebConstants.CHECK_TRAIN_LIVE_API_SERVICE, response.responseCode);
            } else {
                serviceListener.onServiceSuccess(response, WebConstants.CHECK_TRAIN_LIVE_API_SERVICE);
            }
        }
    }

}
