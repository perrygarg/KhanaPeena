package com.perrygarg.khanapeena.common.network;

/**
 * Created by perry.garg on 30/01/17.
 */

public interface WebConstants {

    /**
     * Web Service Task Codes
     */
    int WS_CODE_TRAIN_AUTOCOMPLETE = 1;
    int FETCH_SERVING_STATIONS_SERVICE = 2;
    int FETCH_TRAIN_ROUTE_SERVICE = 3;

    String RAIL_API_KEY = "/apikey/m4h2fi5539";

    /**
     * Web Service URLs
     */
    //String BASE_URL = ""; //Production Server
    String BASE_URL_RAIL_API = "http://api.railwayapi.com/";
    //String URL_LOGIN = BASE_URL + "v1/business/login";
    String URL_TRAIN_AUTOCOMPLETE = BASE_URL_RAIL_API + "v2/suggest-train/train/";
    String URL_TRAIN_ROUTE = BASE_URL_RAIL_API + "v2/route/train/";

    //Firebase schemas
    String SERVING_STATIONS_SCHEMA = "stations_served";

}
