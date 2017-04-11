package com.project.yang.m.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.project.yang.m.R;
import com.project.yang.m.beans.UserInfo;
import com.project.yang.m.databinding.ActivityRegisterBinding;
import com.project.yang.m.network.ProgressSubscriber;
import com.project.yang.m.network.RetrofitHandler;
import com.project.yang.m.service.UserService;
import com.project.yang.m.utils.LogUtil;
import com.project.yang.m.utils.ToastUtil;
import com.project.yang.m.utils.Utils;

import okhttp3.ResponseBody;

/**
 * @Author: NiYang
 * @Date: 2017/4/9.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding binding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_register, null, false);
        setContentView(this.binding.getRoot());
        initView();
    }

    private void initView() {
        this.binding.btnConfirm.setOnClickListener(this);
        this.binding.ivExit.setOnClickListener(this);
    }

    private void registerUser() {
        long startTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(this.binding.etUserName.getText())) {
            ToastUtil.showToast("用户名不能为空");
            Utils.buttonAnimation(this.binding.btnConfirm);
        } else if (TextUtils.isEmpty(this.binding.etUserPassword.getText())) {
            ToastUtil.showToast("密码不能为空");
            Utils.buttonAnimation(this.binding.btnConfirm);
        } else if (TextUtils.isEmpty(this.binding.etConfirmPassword.getText())) {
            ToastUtil.showToast("确认密码不能为空");
            Utils.buttonAnimation(this.binding.btnConfirm);
        } else if (TextUtils.isEmpty(this.binding.etRealName.getText())) {
            ToastUtil.showToast("姓名不能为空");
            Utils.buttonAnimation(this.binding.btnConfirm);
        } else if (TextUtils.isEmpty(this.binding.etSex.getText())) {
            ToastUtil.showToast("性别不能为空");
            Utils.buttonAnimation(this.binding.btnConfirm);
        } else if (TextUtils.isEmpty(this.binding.etBirthday.getText())) {
            ToastUtil.showToast("出生年月不能为空");
            Utils.buttonAnimation(this.binding.btnConfirm);
        } else if (TextUtils.isEmpty(this.binding.etPhone.getText())) {
            ToastUtil.showToast("电话不能为空");
            Utils.buttonAnimation(this.binding.btnConfirm);
        } else if (TextUtils.isEmpty(this.binding.etEmail.getText())) {
            ToastUtil.showToast("邮箱不能为空");
            Utils.buttonAnimation(this.binding.btnConfirm);
        } else if (!this.binding.etUserPassword.getText().toString().equals(this.binding.etConfirmPassword.getText().toString())) {
            ToastUtil.showToast("两次密码输入不一致");
            Utils.buttonAnimation(this.binding.btnConfirm);
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(this.binding.etUserName.getText().toString());
            userInfo.setPassword(this.binding.etUserPassword.getText().toString());
            userInfo.setGender("男".equals(this.binding.etSex.getText().toString()) ? 1 : 0);
            userInfo.setBirthday(Utils.stringTransformDate(this.binding.etBirthday.getText().toString()));
            userInfo.setPhone(this.binding.etPhone.getText().toString());
            userInfo.setEmail(this.binding.etEmail.getText().toString());

            registerRequest(userInfo);
        }
        long endTime = System.currentTimeMillis();
        LogUtil.d(TAG, String.valueOf(endTime - startTime));
    }

    /**
     * 注册的网络请求
     *
     * @param userInfo
     */
    private void registerRequest(UserInfo userInfo) {
        RetrofitHandler.getService(UserService.class).registerUser(userInfo).subscribe(new ProgressSubscriber<ResponseBody>(this) {
            @Override
            protected void onFail(Throwable e) {
                LogUtil.e(TAG, e.getMessage());
            }

            @Override
            protected void onSuccess(ResponseBody s) {
                ToastUtil.showToast("注册用户成功");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_exit:
                finish();
                break;
            case R.id.btn_confirm:
                registerUser();
                break;
            default:
                break;
        }
    }
}
