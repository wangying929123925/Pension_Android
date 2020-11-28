package com.warehouse.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    public static Application sApplication;

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        context = getApplicationContext();
    }

    public static Context getContex() {
        return context;
    }


}
