package com.project.yang.m.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.project.yang.m.R;

/**
 * Created by NiYang on 2017/4/16.
 */

public class CustomBottomDialog extends Dialog {
    private Context mContext = null;

    public CustomBottomDialog(View contentView, Context context) {
        super(context);
        this.mContext = context;
        init(contentView);
    }

    public CustomBottomDialog(View contentView, Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init(contentView);
    }

    protected CustomBottomDialog(View contentView, Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        init(contentView);
    }

    private void init(View contentView) {
        setContentView(contentView);
        Window window = getWindow();//获取Dialog的Window对象
        window.setGravity(Gravity.BOTTOM);//把Dialog的窗体设置在屏幕的底部
        window.setWindowAnimations(R.style.dialog_animation);//给窗体设置动画

        //获取的是Dialog窗体的布局参数,注意这里不是Dialog中的View的布局参数
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = 15;//设置Dialog的窗体距离屏幕底部的距离
        //获取Activity的Window管理器
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        //设置Dialog的窗体的宽度,屏幕的宽度-40
        lp.width = windowManager.getDefaultDisplay().getWidth()-30;
        window.setAttributes(lp);
    }

    public void showDialog() {
        show();
    }
}
