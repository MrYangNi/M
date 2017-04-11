package com.project.yang.m.setting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.project.yang.m.R;
import com.project.yang.m.common.BaseActivity;
import com.project.yang.m.databinding.ActivitySettingBinding;

/**
 * Created by NiYang on 2017/3/29.
 */

public class SettingActivity extends BaseActivity {
    private ActivitySettingBinding binding = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("设置");
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_setting, null, false);
        setContentView(this.binding.getRoot());
    }
}
