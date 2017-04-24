package com.project.yang.m.common;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.DatePicker;

import com.project.yang.m.R;
import com.project.yang.m.databinding.DialogDateSelectorViewBinding;

import java.util.Calendar;

/**
 * Created by NiYang on 2017/4/24.
 */

public class DateSelectorDialog extends Dialog {
    private DialogDateSelectorViewBinding binding = null;
    private OnDateChangeListener listener = null;

    public DateSelectorDialog(Context context) {
        super(context);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_date_selector_view, null, false);
        setContentView(this.binding.getRoot());
        initDatePicker();
    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        this.binding.datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                listener.onDateChange(year, monthOfYear, dayOfMonth);
                dismiss();
            }
        });
    }

    public interface OnDateChangeListener {
        void onDateChange(int year, int month, int dayOfMonth);
    }

    public void registerListener(OnDateChangeListener listener) {
        this.listener = listener;
    }

}
