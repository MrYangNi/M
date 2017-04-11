package com.project.yang.m.personal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.yang.m.R;
import com.project.yang.m.databinding.FragmentHelpBinding;

/**
 * Created by NiYang on 2017/4/5.
 */

public class HelpFragment extends PersonalBaseFragment implements View.OnClickListener {
    private FragmentHelpBinding binding = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false);
        this.binding.btnLastStep.setOnClickListener(this);
        return this.binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        returnLastStep();
    }
}
