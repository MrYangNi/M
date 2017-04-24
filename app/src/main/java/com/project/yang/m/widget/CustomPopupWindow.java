package com.project.yang.m.widget;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by NiYang on 2017/4/16.
 */

public class CustomPopupWindow extends PopupWindow {
    private int topLayout = 0;
    private View contentView = null;
    public CustomPopupWindow(View contentView,int topLayout) {
        //设置PopupWindow的布局
        setContentView(contentView);
        this.contentView = contentView;
        this.topLayout = topLayout;
        initParams();
    }

    private void initParams() {
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable colorDrawable = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(colorDrawable);
        this.contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = contentView.findViewById(topLayout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
