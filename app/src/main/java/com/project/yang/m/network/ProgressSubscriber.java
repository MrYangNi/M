package com.project.yang.m.network;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.project.yang.m.R;
import com.project.yang.m.utils.ToastUtil;
import rx.Subscriber;


/**
 * @Author: NiYang
 * @Date: 2017/4/9.
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> {
    private static final String TAG = "ProgressSubscriber";
    private Context mContext = null;
    private CustomProgressDialog progressDialog = null;
    private static final int TIME_OUT = 20000;//20秒超时

    public ProgressSubscriber(Context context) {
        this.mContext = context;
        this.progressDialog = new CustomProgressDialog(this.mContext);
        this.progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ToastUtil.showToast("请求已取消");
            }
        });
        this.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!isUnsubscribed()) {
                    unsubscribe();
                }
            }
        });
        showProgressBar();
    }

    @Override
    public void onCompleted() {
        dismissProgressBar();
    }

    @Override
    public void onError(Throwable e) {
        RetrofitException.httpException(e);
        dismissProgressBar();
        onFail(e);
    }

    @Override
    public void onNext(T t) {
        dismissProgressBar();
        onSuccess(t);
    }

    protected abstract void onFail(Throwable e);

    protected abstract void onSuccess(T t);

    private void showProgressBar() {
        if (this.progressDialog != null && !this.progressDialog.isShowing()) {
            this.progressDialog.show();
            this.progressDialog.getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing()) {
                        ToastUtil.showToast("请求超时");
                        dismissProgressBar();
                    }
                }
            }, TIME_OUT);
        }
    }

    private void dismissProgressBar() {
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
            unsubscribe();
        }
    }

    private class CustomProgressDialog extends Dialog{

        public CustomProgressDialog(Context context) {
            super(context, R.style.CustomProgressDialog);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_progress_bar);
        }
    }

}
