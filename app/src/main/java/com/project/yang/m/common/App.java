package com.project.yang.m.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by NiYang on 2016/12/8.
 */

public class App extends Application {
    private static Context mContext;
    private static App app;

    public static Application getApplication() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        this.mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
