package com.project.yang.m.other;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.project.yang.m.R;
import com.project.yang.m.common.BaseActivity;
import com.project.yang.m.databinding.ActivityOtherBinding;

/**
 * Created by NiYang on 2017/3/30.
 */

public class OtherActivity extends BaseActivity {
    private ActivityOtherBinding binding = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("其他");
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_other, null, false);
        setContentView(this.binding.getRoot());
    }
}
