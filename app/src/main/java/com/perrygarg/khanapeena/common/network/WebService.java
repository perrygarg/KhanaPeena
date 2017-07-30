package com.perrygarg.khanapeena.common.network;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.application.MyApplication;
import com.perrygarg.khanapeena.common.constants.AppConstants;
import com.perrygarg.khanapeena.common.model.MasterResponse;
import com.perrygarg.khanapeena.common.model.ValidationError;
import com.perrygarg.khanapeena.common.util.AppLogs;

/**
 * Created by perry.garg on 02/02/17.
 */

public abstract class WebService implements Response.Listener, Response.ErrorListener{
    protected String tag;
    protected int taskCode;
    protected WebServiceListener serviceListener = null;

    public WebService(int taskCode, WebServiceListener serviceListener)
    {
        this.taskCode = taskCode;
        this.serviceListener = serviceListener;
        this.tag = String.valueOf(taskCode);

        if(serviceListener != null)
            serviceListener.onServiceBegin(taskCode);
    }

    /**
     * Method for making request to the server using Volley and GSON. Override this method in your service class for making request to the server API's.
     * @param args Your request
     */
    abstract public void getData(Object... args);

    @Override
    public void onResponse(Object object)
    {
        if(serviceListener != null)
        {
            serviceListener.onServiceSuccess((MasterResponse) object, taskCode);
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError)
    {
        AppLogs.d(AppLogs.TAG, volleyError.getMessage());

        if(serviceListener != null)
        {
            if(volleyError.networkResponse != null && volleyError.networkResponse.statusCode == 400)
            {
                ValidationError[] validationError = null;

                try
                {
                    String str = new String(volleyError.networkResponse.data, "utf-8");
                    validationError = getValidationError(str);
                }
                catch(Exception exception)
                {}

                if(serviceListener != null)
                    serviceListener.onValidationError(validationError, taskCode);
            }
            else if(volleyError instanceof TimeoutError)
            {
                serviceListener.onServiceError(MyApplication.appContext.getString(R.string.error_time_out), taskCode, AppConstants.ERROR_TYPE_SIMPLE);
            }
            else if(volleyError instanceof NetworkError)
            {
                serviceListener.onServiceError(MyApplication.appContext.getString(R.string.error_network), taskCode, AppConstants.ERROR_TYPE_NO_NETWORK);
            }
            else
            {
                serviceListener.onServiceError(MyApplication.appContext.getString(R.string.error_tech), taskCode, AppConstants.ERROR_TYPE_SIMPLE);
            }
        }
    }

    private ValidationError[] getValidationError(String error)
    {
        ValidationError[] validationError = null;
        Gson gson = new Gson();

        if(error != null)
        {
            validationError = gson.fromJson(error, ValidationError[].class);
        }
        return validationError;
    }
}
