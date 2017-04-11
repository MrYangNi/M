package com.project.yang.m.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.project.yang.m.R;
import com.project.yang.m.beans.LoginInfo;
import com.project.yang.m.beans.UserAuth;
import com.project.yang.m.databinding.ActivityTestBinding;
import com.project.yang.m.network.DefaultSubscriber;
import com.project.yang.m.network.RetrofitHandler;
import com.project.yang.m.service.LoginService;
import com.project.yang.m.utils.LogUtil;

/**
 * Created by NiYang on 2017/3/28.
 */

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private ActivityTestBinding binding = null;
    private ActionBarDrawerToggle toggle = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_test, null, false);
        setContentView(this.binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.toggle = new ActionBarDrawerToggle(this, this.binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.binding.drawerLayout.setDrawerListener(this.toggle);
        this.toggle.syncState();

        LoginInfo loginInfo = new LoginInfo("1", "Jayce", "111111");
        RetrofitHandler.getService(LoginService.class).login(loginInfo).subscribe(new DefaultSubscriber<UserAuth>() {
            @Override
            protected void onSuccess(UserAuth userAuth) {
                System.out.println("================\n"+ userAuth.toString());
            }

            @Override
            protected void onFail(Throwable e) {

            }
        });
        RetrofitHandler.getService(LoginService.class).logout().subscribe(new DefaultSubscriber() {
            @Override
            protected void onSuccess(Object o) {
                LogUtil.d(TAG,o.toString());
            }

            @Override
            protected void onFail(Throwable e) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
