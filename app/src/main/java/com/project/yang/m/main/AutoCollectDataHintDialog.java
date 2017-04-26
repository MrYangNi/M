package com.project.yang.m.main;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.project.yang.m.R;
import com.project.yang.m.databinding.DialogAutoCollectDataViewBinding;

/**
 * Created by NiYang on 2017/4/26.
 */

public class AutoCollectDataHintDialog extends Dialog implements View.OnClickListener {
    private DialogAutoCollectDataViewBinding binding = null;
    private OnCollectDataListener listener = null;

    public AutoCollectDataHintDialog(Context context) {
        super(context);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_auto_collect_data_view, null, false);
        setContentView(this.binding.getRoot());
        this.binding.txtStartAutoCollectData.setOnClickListener(this);
        this.binding.txtEndAutoCollectData.setOnClickListener(this);
    }

    public void setOnCollectDataListener(OnCollectDataListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_start_auto_collect_data:
                this.listener.onStartAutoCollectDataListener();
                dismiss();
                break;
            case R.id.txt_end_auto_collect_data:
                this.listener.onEndAutoCollectDataListener();
                dismiss();
                break;
            default:break;
        }
    }
}
