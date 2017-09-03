package com.perrygarg.khanapeena.common.network;

import com.perrygarg.khanapeena.application.MyApplication;
import com.perrygarg.khanapeena.home.service.TrainAutoCompleteService;
import com.perrygarg.khanapeena.home.service.TrainRouteService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by perry.garg on 01/02/17.
 */

public class WebManager {

    /**
     * Get Service Instance for a Tasks
     * @param taskCode - The Task code
     * @param serviceListener - Listener to send Success or Failure Callbacks
     * @return - Service Instance
     */
    public static WebService getService(int taskCode, WebServiceListener serviceListener)
    {
        switch (taskCode)
        {
            case WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE:
                return new TrainAutoCompleteService(taskCode, serviceListener);
            case WebConstants.FETCH_TRAIN_ROUTE_SERVICE:
                return new TrainRouteService(taskCode, serviceListener);
        }

        return null;
    }

    /**
     * Get headers
     * @return
     */
    public static Map<String , String> getHeaders()
    {
        Map<String, String> headers = new HashMap<>();

        //Place headers required in your API's here
//        headers.put(WebConstants.HEADER_API_ID_KEY, WebConstants.HEADER_API_ID_VALUE);
//        headers.put(WebConstants.HEADER_API_VERSION_KEY, WebConstants.HEADER_API_VERSION_VALUE);
//        headers.put(WebConstants.HEADER_DEVICE_KEY, WebConstants.HEADER_DEVICE_VALUE);
//        headers.put(WebConstants.HEADER_DEVICE_ID_KEY, String.valueOf(AppUtil.getAppVersionCode()));
//
//        String token = PersistenceManager.getToken();
//        if(token != null)
//        {
//            headers.put(WebConstants.HEADER_API_TOKEN_KEY, token);
//        }
        return headers;
    }

    public static void cancelService(int taskCode)
    {
        AppHttpClient appHttpClient = new AppHttpClient(MyApplication.appContext);
        appHttpClient.cancelRequests(String.valueOf(taskCode));
    }

}
