package com.project.yang.m.common;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by NiYang on 2017/4/24.
 */

public class ProgressDialogManager {
    private static ProgressDialogManager progressDialogManager = null;
    private ProgressDialog progressDialog = null;

    private ProgressDialogManager() {
    }

    public static ProgressDialogManager getInstance() {
        if (progressDialogManager == null) {
            synchronized (ProgressDialogManager.class) {
                if (progressDialogManager == null) {
                    progressDialogManager = new ProgressDialogManager();
                }
            }
        }
        return progressDialogManager;
    }

    public void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
    }
}
