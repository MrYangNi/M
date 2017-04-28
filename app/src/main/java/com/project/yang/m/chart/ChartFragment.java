package com.project.yang.m.chart;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.yang.m.R;
import com.project.yang.m.databinding.FragmentChartViewBinding;

/**
 * Created by NiYang on 2017/4/28.
 */

public class ChartFragment extends Fragment {
    private FragmentChartViewBinding binding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_chart_view, container, false);
        return this.binding.getRoot();
    }
}
