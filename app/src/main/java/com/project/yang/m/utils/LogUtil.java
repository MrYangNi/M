package com.project.yang.m.utils;

import android.util.Log;

/**
 * 自定义日志工具类，用于控制日志输出
 * Created by NiYang on 2016/12/8.
 */

public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 0;

    public static final int LEVEL = ERROR;

    public static void v(String tag, String msg) {
        if (VERBOSE <= LEVEL) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG <= LEVEL) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (INFO <= LEVEL) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (WARN <= LEVEL) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (ERROR <= LEVEL) {
            Log.e(tag, msg);
        }
    }

}
