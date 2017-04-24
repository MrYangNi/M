package com.project.yang.m.personal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.project.yang.m.R;
import com.project.yang.m.common.BaseActivity;
import com.project.yang.m.databinding.ActivityPersonalCenterBinding;
import com.project.yang.m.login.LoginActivity;
import com.project.yang.m.login.RegisterActivity;
import com.project.yang.m.stores.Pref;

/**
 * 目前只有“我的信息”，“设置”可操作
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
        this.binding.rlSetting.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginOrRegister();
    }

    private void loginOrRegister() {
        if (Pref.getBoolean(Pref.Key.User.IS_LOGIN, false) && Pref.getString(Pref.Key.User.USER_NAME, null) != null) {
            this.binding.txtLoginOrRegister.setText(Pref.getString(Pref.Key.User.USER_NAME, null));
        } else {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append("登录/注册");
            spannableStringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ((TextView) widget).setHighlightColor(ContextCompat.getColor(PersonalCenterActivity.this, R.color.transparent));
                    //跳转到登录界面
                    Intent intent = new Intent(PersonalCenterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, 0, 2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableStringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ((TextView) widget).setHighlightColor(ContextCompat.getColor(PersonalCenterActivity.this, R.color.transparent));
                    //跳转到注册界面
                    Intent intent = new Intent(PersonalCenterActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, 3, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            this.binding.txtLoginOrRegister.setText(spannableStringBuilder);
            this.binding.txtLoginOrRegister.setMovementMethod(LinkMovementMethod.getInstance());
        }
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
            case R.id.rl_personal_info:
                Intent personalInfoIntent = new Intent(this, PersonalInfoActivity.class);
                startActivity(personalInfoIntent);
                break;
            case R.id.rl_help:
                break;
            case R.id.rl_version:
                break;
            case R.id.rl_setting:
                Intent settingIntent = new Intent(this, PersonalSettingActivity.class);
                startActivity(settingIntent);
                break;
            default:
                break;
        }
    }
}
