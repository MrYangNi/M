package com.project.yang.m.personal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.project.yang.m.R;
import com.project.yang.m.common.BaseActivity;
import com.project.yang.m.databinding.ActivityPersonalCenterBinding;
import com.project.yang.m.personal.fragment.AlterMyInfoFragment;
import com.project.yang.m.personal.fragment.HelpFragment;
import com.project.yang.m.personal.fragment.MyInfoFragment;
import com.project.yang.m.personal.fragment.VersionFragment;

/**
 * Created by NiYang on 2017/3/29.
 */

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {
    private ActivityPersonalCenterBinding binding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("我的");
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_personal_center, null, false);
        setContentView(this.binding.getRoot());
        this.binding.modificationHead.setOnClickListener(this);
        this.binding.rlPersonalInfo.setOnClickListener(this);
        this.binding.rlHelp.setOnClickListener(this);
        this.binding.rlVersion.setOnClickListener(this);
        this.binding.llExit.setOnClickListener(this);
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.push_in, R.anim.push_out);
        transaction.replace(R.id.main_fragment_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modification_head:
                MyInfoFragment myInfoFragment = new MyInfoFragment();
                switchFragment(myInfoFragment);
                break;
            case R.id.rl_personal_info:
                AlterMyInfoFragment alterMyInfoFragment = new AlterMyInfoFragment();
                switchFragment(alterMyInfoFragment);
                break;
            case R.id.rl_help:
                HelpFragment helpFragment = new HelpFragment();
                switchFragment(helpFragment);
                break;
            case R.id.rl_version:
                VersionFragment versionFragment = new VersionFragment();
                switchFragment(versionFragment);
                break;
            case R.id.ll_exit:
                break;
            default:break;
        }
    }
}
