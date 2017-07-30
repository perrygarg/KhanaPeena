package com.perrygarg.khanapeena.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by PerryGarg on 30-07-2017.
 */

public class MyApplication extends Application {

    public static Context appContext;
    public static MyApplication myApplication;

    @Override
    public void onCreate()
    {
        super.onCreate();

        //Prepare Application
        appContext = this;
        myApplication = this;
    }

    public static MyApplication getAppInstance() {
        return myApplication;
    }

}
