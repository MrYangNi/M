package com.project.yang.m.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.project.yang.m.R;
import com.project.yang.m.databinding.TestActivityWheelViewBinding;
import com.project.yang.m.widget.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by NiYang on 2017/4/16.
 */

public class WheelViewTestActivity extends AppCompatActivity {
    private TestActivityWheelViewBinding binding = null;
    private String year = "";
    private String month = "";
    private String date = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.test_activity_wheel_view, null, false);
        setContentView(this.binding.getRoot());
        Calendar calendar = Calendar.getInstance();
//        this.binding.wheelViewYear.setSeletion(calendar.get(Calendar.YEAR));
//        this.binding.wheelViewMonth.setSeletion(calendar.get(Calendar.MONTH)+1);
        this.year = String.valueOf(calendar.get(Calendar.YEAR)+"年");
        calendar.set(Calendar.MONTH,0);
        this.month = String.valueOf(calendar.get(Calendar.MONTH) + 1 + "月");
        initYearWheelView();
        this.binding.wheelViewYear.setSeletion(initYear(10).size() - 1);
        initMonthWheelView();
        initDateWheelView();
        this.binding.wheelViewYear.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                year = item;
                initMonthWheelView();
            }
        });

        this.binding.wheelViewMonth.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                month = item;
                initDateWheelView();
            }
        });

        this.binding.wheelViewDate.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                date = item;
            }
        });
    }

    private List<Integer> initYear(int interval) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        //取当前年的前60年
        List<Integer> yearList = new ArrayList<>();
        int startYear = currentYear - interval;
        for (int i = 0; i <= interval; i++) {
            yearList.add(startYear + i);
        }
        return yearList;
    }

    private void initYearWheelView() {
        List<String> yearStringList = new ArrayList<>();
        for (Integer num : initYear(10)) {
            yearStringList.add(num + "年");
        }
        this.binding.wheelViewYear.setOffset(1);
        this.binding.wheelViewYear.setItems(yearStringList);
    }

    private Integer getYear(String str) {
        String[] array = str.split("年");
        return Integer.valueOf(array[0]);
    }

    private Integer getMonth() {
        String[] array = month.split("月");
        return Integer.valueOf(array[0]);
    }

    private void initMonthWheelView() {
        String[] month = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        this.binding.wheelViewMonth.setOffset(1);
        this.binding.wheelViewMonth.setItems(Arrays.asList(month));
    }

    private void initDateWheelView() {
        Integer selectedYear = getYear(year);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,selectedYear);
        calendar.set(Calendar.MONTH, getMonth()-1);
        List<String> dateList = new ArrayList<>();
        for (int i =1;i<=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
            dateList.add(i+"日");
        }
        this.binding.wheelViewDate.setOffset(1);
        this.binding.wheelViewDate.setItems(dateList);
    }

}
