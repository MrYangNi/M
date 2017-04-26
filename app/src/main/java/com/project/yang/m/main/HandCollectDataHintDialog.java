package com.project.yang.m.main;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.project.yang.m.R;
import com.project.yang.m.databinding.DialogHandCollectDataViewBinding;

/**
 * Created by NiYang on 2017/4/26.
 */

public class HandCollectDataHintDialog extends Dialog implements View.OnClickListener {
    private DialogHandCollectDataViewBinding binding = null;
    private OnCollectDataListener listener = null;

    public HandCollectDataHintDialog(Context context) {
        super(context);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_hand_collect_data_view, null, false);
        setContentView(this.binding.getRoot());
        this.binding.txtStartHandCollectData.setOnClickListener(this);
        this.binding.txtEndHandCollectData.setOnClickListener(this);
    }

    public void setOnCollectDataListener(OnCollectDataListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_start_hand_collect_data:
                this.listener.onStartHandCollectDataListener();
                dismiss();
                break;
            case R.id.txt_end_hand_collect_data:
                this.listener.onEndHandCollectDataListener();
                dismiss();
                break;
            default:break;
        }
    }
}
