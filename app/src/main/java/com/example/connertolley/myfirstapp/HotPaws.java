package com.example.connertolley.myfirstapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by connertolley on 7/1/17.
 */

public class HotPaws extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        HotPaws.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return HotPaws.context;
    }

}
