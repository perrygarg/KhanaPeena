package com.perrygarg.khanapeena.common.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;

import java.util.Map;

/**
 * Created by perry.garg on 30/01/17.
 */

public class AppHttpClient {

    private Context context;
    private NetworkHandler networkHandler;

    private final int TIMEOUT = 20000; //In Millis

    public AppHttpClient(Context context)
    {
        this.context = context;
        this.networkHandler = NetworkHandler.getInstance(this.context);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important to
     * specify a TAG so that the pending/ongoing requests can be cancelled.
     */
    public void cancelRequests(Object tag)
    {
        networkHandler.cancelRequests(tag);
    }

    /**
     * Custom Request Method to send a GSON request to Volley
     */
    public <K, T> void sendGSONRequest(int method, String url, K clsRequest, Class<T> clsResponse,
                                       final Map<String, String> headers, Response.Listener<T> responseListener,
                                       Response.ErrorListener errorListener, String tag)
    {
        GsonRequest<T, K> gsonRequest = new GsonRequest<>(method, url, clsRequest, clsResponse, headers, responseListener, errorListener);
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, 0, 0.0F));
        gsonRequest.setShouldCache(false); //TODO cache


        //Add To Volley Request Queue
        networkHandler.addToRequestQueue(gsonRequest, tag);
    }

}
