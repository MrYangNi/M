package com.project.yang.m.personal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.project.yang.m.R;
import com.project.yang.m.common.BaseActivity;
import com.project.yang.m.databinding.ActivitySettingBinding;
import com.project.yang.m.network.ProgressSubscriber;
import com.project.yang.m.network.RetrofitHandler;
import com.project.yang.m.service.LoginService;
import com.project.yang.m.stores.Pref;
import com.project.yang.m.utils.ToastUtil;

import okhttp3.ResponseBody;

/**
 * 目前仅有登出操作一个
 * Created by NiYang on 2017/4/13.
 */

public class PersonalSettingActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySettingBinding binding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置");
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_setting, null, false);
        setContentView(this.binding.getRoot());
        this.binding.llExit.setOnClickListener(this);
        isShowExitLoginButton();
    }

    private void isShowExitLoginButton() {
        if (Pref.getBoolean(Pref.Key.User.IS_LOGIN, false)) {
            this.binding.llExit.setVisibility(View.VISIBLE);
        } else {
            this.binding.llExit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        logoutRequest();
    }

    private void logoutRequest() {
        RetrofitHandler.getService(LoginService.class).logout().subscribe(new ProgressSubscriber<ResponseBody>(this) {
            @Override
            protected void onFail(Throwable e) {
                ToastUtil.showToast("登出失败");
            }

            @Override
            protected void onSuccess(ResponseBody responseBody) {
                ToastUtil.showToast("账号已登出");
                Pref.clearPrefData();
                finish();
            }
        });
    }
}
