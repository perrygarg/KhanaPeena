package com.perrygarg.khanapeena.common.network;


import com.perrygarg.khanapeena.common.model.MasterResponse;
import com.perrygarg.khanapeena.common.model.ValidationError;

/**
 * Created by perry.garg on 30/01/17.
 */

public interface WebServiceListener {

    /**
     * Callback method indicating start event of the service. Call it from your service class in order to give begin callback to the presenter layer.
     * @param taskCode : Web service task code against which service has began. This is used for web service type identification.
     */
    void onServiceBegin(int taskCode);

    /**
     * Callback method on service success. Call it from your service class in order to give success callback to the presenter layer.
     * @param masterResponse : Response model from the server API
     * @param taskCode : Web service task code against which response is taken. This is used for web service type identification.
     */

    void onServiceSuccess(MasterResponse masterResponse, int taskCode);

    /**
     * Callback method on service error. Call it from your service class in order to give error callback to the presenter layer.
     * @param message : Error message.
     * @param taskCode : Web service task code against which error is taken. This is used for web service type identification.
     * @param errorType : Error type.
     */
    void onServiceError(String message, int taskCode, int errorType);
    void onValidationError(ValidationError[] validationError, int taskCode);

}
