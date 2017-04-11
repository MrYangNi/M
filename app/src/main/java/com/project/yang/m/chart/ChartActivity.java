package com.project.yang.m.chart;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.project.yang.m.R;
import com.project.yang.m.common.BaseActivity;
import com.project.yang.m.databinding.ActivityChartBinding;

/**
 * Created by NiYang on 2017/3/30.
 */

public class ChartActivity extends BaseActivity {
    private ActivityChartBinding binding = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("图表分析");
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_chart, null, false);
        setContentView(this.binding.getRoot());
    }
}
