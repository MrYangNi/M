package com.project.yang.m.common;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

/**
 * Created by NiYang on 2016/12/8.
 */

public class MApplication extends Application {
    private static Context mContext;
    private static MApplication mApplication;

    public static Application getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        this.mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
