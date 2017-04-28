package com.project.yang.m.historyrecord;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.project.yang.m.R;
import com.project.yang.m.databinding.PopupWindowHistoryRecordTimeBinding;
import com.project.yang.m.utils.Utils;
import com.project.yang.m.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiYang on 2017/4/27.
 */

public class HistoryRecordTimePopupWindow extends PopupWindow implements View.OnClickListener {
    private PopupWindowHistoryRecordTimeBinding binding = null;
    private OnSelectedHistoryTimeListener listener = null;
    private String time = null;

    public HistoryRecordTimePopupWindow(Context context, List<String> startTimeData,List<String> endTimeData) {
        super(context);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.popup_window_history_record_time, null, false);
        setContentView(this.binding.getRoot());
        setPopupWindowParams();

        List<String> timeData = packageTime(startTimeData, endTimeData);
        this.binding.timeWheelView.setOffset(1);
        this.binding.timeWheelView.setItems(timeData);
        this.binding.timeWheelView.setSeletion(0);
        this.time = timeData.get(0);
        this.binding.timeWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                time = item;
            }
        });

        this.binding.txtCancel.setOnClickListener(this);
        this.binding.txtConfirm.setOnClickListener(this);
    }

    private void setPopupWindowParams() {
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.dialog_animation);
        this.setBackgroundDrawable(new BitmapDrawable());
    }

    public void setOnSelectedHistoryTimeListener(OnSelectedHistoryTimeListener listener) {
        this.listener = listener;
    }

    private List<String> packageTime(List<String> startTime,List<String> endTime) {
        List<String> timeQuantum = new ArrayList<>();
        if (startTime.size() == endTime.size()) {
            for (int i =0;i<startTime.size();i++) {
                timeQuantum.add(startTime.get(i) + " 至 " + endTime.get(i));
            }
        }
        return timeQuantum;
    }

    private List<Long> singleTime(String time) {
        String[] data = time.split("至");
        List<Long> startAndEndTime = new ArrayList<>();
        startAndEndTime.add(Utils.transformDate(data[0].trim()));
        startAndEndTime.add(Utils.transformDate(data[1].trim()));
        return startAndEndTime;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_cancel:
                dismiss();
                break;
            case R.id.txt_confirm:
                if (this.time != null) {
                    listener.onSelectedHistoryTime(singleTime(this.time).get(0), singleTime(this.time).get(1));
                }
                dismiss();
                break;
            default:break;
        }
    }
}
