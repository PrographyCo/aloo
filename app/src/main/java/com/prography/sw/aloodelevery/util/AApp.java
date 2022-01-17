package com.prography.sw.aloodelevery.util;

import android.app.Application;
import android.content.Context;

public class AApp extends Application {
    private static Application instance;
//    private Socket mSocket;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

}
