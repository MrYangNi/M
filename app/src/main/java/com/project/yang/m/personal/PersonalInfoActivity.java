package com.project.yang.m.personal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.project.yang.m.R;
import com.project.yang.m.beans.UserInfo;
import com.project.yang.m.common.BaseActivity;
import com.project.yang.m.databinding.ActivityPersonalInfoBinding;
import com.project.yang.m.network.ProgressSubscriber;
import com.project.yang.m.network.RetrofitHandler;
import com.project.yang.m.service.UserService;
import com.project.yang.m.stores.Pref;
import com.project.yang.m.utils.ToastUtil;
import com.project.yang.m.utils.Utils;

/**
 * 用户详细信息，只有展示信息的作用，没有其他操作
 * Created by NiYang on 2017/4/13.
 */

public class PersonalInfoActivity extends BaseActivity {
    private ActivityPersonalInfoBinding binding = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("我的信息");
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_personal_info, null, false);
        setContentView(this.binding.getRoot());

        getUserDetailInfo();
    }

    private void getUserDetailInfo() {
        if (Pref.getBoolean(Pref.Key.User.IS_LOGIN, false) && Pref.getLong(Pref.Key.User.USER_ID, -1L) != -1L) {
            RetrofitHandler.getService(UserService.class).getUserDetail(Pref.getLong(Pref.Key.User.USER_ID, -1L))
                    .subscribe(new ProgressSubscriber<UserInfo>(PersonalInfoActivity.this) {
                @Override
                protected void onFail(Throwable e) {
                    ToastUtil.showToast(e.getMessage());
                }

                @Override
                protected void onSuccess(UserInfo userInfo) {
                    if (userInfo == null) {
                        return;
                    }
                    binding.txtNickname.setText(userInfo.getUsername() == null ? "无" : userInfo.getUsername());
                    binding.txtName.setText(userInfo.getName() == null ? "无" : userInfo.getName());
                    binding.txtAge.setText(userInfo.getBirthday() == null ? "无" : Utils.dateTransformString(userInfo.getBirthday()));
                    binding.txtPhone.setText(userInfo.getPhone() == null ? "无" : userInfo.getPhone());
                    binding.txtSex.setText(userInfo.getGender() == null ? "无" : userInfo.getGender() == 1 ? "男" : "女");
                    binding.txtEmail.setText(userInfo.getEmail() == null ? "无" : userInfo.getEmail());
                }
            });
        } else {
            ToastUtil.showToast("您未登陆");
        }
    }
}
