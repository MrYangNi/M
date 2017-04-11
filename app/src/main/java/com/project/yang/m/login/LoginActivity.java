package com.project.yang.m.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.project.yang.m.R;
import com.project.yang.m.beans.LoginInfo;
import com.project.yang.m.beans.UserAuth;
import com.project.yang.m.databinding.ActivityLoginBinding;
import com.project.yang.m.main.MainActivity;
import com.project.yang.m.network.AuthManager;
import com.project.yang.m.network.ProgressSubscriber;
import com.project.yang.m.network.RetrofitHandler;
import com.project.yang.m.service.LoginService;
import com.project.yang.m.stores.Pref;
import com.project.yang.m.utils.LogUtil;
import com.project.yang.m.utils.ToastUtil;
import com.project.yang.m.utils.Utils;

/**
 * @Author: NiYang
 * @Date: 2017/4/9.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_login, null, false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(this.binding.getRoot());

        this.binding.ivExit.setOnClickListener(this);
        this.binding.txtRegister.setOnClickListener(this);
        this.binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_exit:
                finish();
                break;
            case R.id.txt_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                login();
                break;
            default:
                break;
        }
    }

    /**
     * 用户登录请求前的相关处理
     */
    private void login() {
        if (!TextUtils.isEmpty(this.binding.etUserName.getText()) && !TextUtils.isEmpty(this.binding.etUserPassword.getText())) {
            loginRequest();
        } else {
            ToastUtil.showToast("用户名或密码不能为空");
            Utils.buttonAnimation(this.binding.btnLogin);
        }
    }

    /**
     *登录请求
     */
    private void loginRequest() {
        LoginInfo loginInfo = new LoginInfo(AuthManager.clientId, this.binding.etUserName.getText().toString(), this.binding.etUserPassword.getText().toString());
        RetrofitHandler.getService(LoginService.class).login(loginInfo).subscribe(new ProgressSubscriber<UserAuth>(this) {
            @Override
            protected void onSuccess(UserAuth userAuth) {
                if (userAuth == null) {
                    return;
                }
                AuthManager.setUserAuth(userAuth);//本地保存用户认证信息，包括userId,name,username,token
                Pref.putLong(Pref.Key.User.USER_ID,userAuth.getUserId());//保存userId
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username",userAuth.getUsername());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            protected void onFail(Throwable e) {
                LogUtil.e(TAG, e.getMessage());
            }
        });
    }
}
