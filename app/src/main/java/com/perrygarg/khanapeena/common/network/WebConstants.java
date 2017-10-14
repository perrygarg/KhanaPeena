package com.perrygarg.khanapeena.common.network;

/**
 * Created by perry.garg on 30/01/17.
 */

public interface WebConstants {

    /**
     * Web Service Task Codes
     */
    int WS_CODE_TRAIN_AUTOCOMPLETE = 1;
    int FETCH_CONFIG_SERVICE = 2;
    int FETCH_TRAIN_ROUTE_SERVICE = 3;
    int CHECK_TRAIN_LIVE_API_SERVICE = 4;
    int FETCH_TRAIN_LIST_SERVICE = 5;

    String RAIL_API_KEY = "/apikey/m4h2fi5539";
    String VERSION = "v2/";
    String DATE = "/date/";

    /**
     * Web Service URLs
     */
    //String BASE_URL = ""; //Production Server
    String BASE_URL_RAIL_API = "http://api.railwayapi.com/";
    //String URL_LOGIN = BASE_URL + "v1/business/login";
    String URL_TRAIN_AUTOCOMPLETE = BASE_URL_RAIL_API + VERSION + "suggest-train/train/";
    String URL_TRAIN_ROUTE = BASE_URL_RAIL_API + VERSION + "route/train/";
    String URL_TRAIN_LIVE_STATUS = BASE_URL_RAIL_API + VERSION + "live/train/";

//    http://api.railwayapi.com/v2/live/train/12045/date/11-09-2017/apikey/m4h2fi5539/

    //Firebase schemas
    String CONFIG_SCHEMA = "config";
    String TRAIN_LIST_SCHEMA = "train_list";

}
