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
import com.perrygarg.khanapeena.home.model.TrainAutoCompleteResponse;
import com.perrygarg.khanapeena.home.model.TrainRouteResponse;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class TrainRouteService extends WebService {

    public TrainRouteService(int taskCode, WebServiceListener serviceListener) {
        super(taskCode, serviceListener);
    }

    @Override
    public void getData(Object... args) {
        Train train = (Train) args[0];

        AppHttpClient httpClient = new AppHttpClient(MyApplication.appContext);
        httpClient.sendGSONRequest(Request.Method.GET, WebConstants.URL_TRAIN_ROUTE + train.number + WebConstants.RAIL_API_KEY, null, TrainRouteResponse.class, WebManager.getHeaders(), this, this, tag);
    }

    @Override
    public void onResponse(Object object)
    {
        TrainRouteResponse response = (TrainRouteResponse) object;
        if(response.responseCode == 200) {
            serviceListener.onServiceSuccess(response, WebConstants.FETCH_TRAIN_ROUTE_SERVICE);
        } else {
            serviceListener.onServiceError(MyApplication.appContext.getString(R.string.api_err_msg), WebConstants.FETCH_TRAIN_ROUTE_SERVICE, response.responseCode);
        }
    }

}
