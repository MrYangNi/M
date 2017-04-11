package com.project.yang.m.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;
import com.project.yang.m.common.MApplication;

/**
 * @Author: NiYang
 * @Date: 2017/4/9.
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(Context context, String info) {
        if (info == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(MApplication.getContext(), info, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(info);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void showToast(Context context, int stringId) {
        showToast(context, context.getResources().getString(stringId));
    }

    public static void showToast(String msg) {
        showToast(MApplication.getContext(), msg);
    }

    public static void showToast(int msgId) {
        showToast(MApplication.getContext(), MApplication.getContext().getString(msgId));
    }

//    public static void showToastAndKillApp(final String msg) {
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(MApplication.getContext(), msg, Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        }.start();
//        try {
//            Thread.sleep(1500);
//        } catch (Exception e) {
//        }
//        MApplication.killProcess();
//    }
}