package com.project.yang.m.test;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.project.yang.m.R;
import com.project.yang.m.beans.LoginInfo;
import com.project.yang.m.beans.UserAuth;
import com.project.yang.m.databinding.ActivityTestBinding;
import com.project.yang.m.network.DefaultSubscriber;
import com.project.yang.m.network.RetrofitHandler;
import com.project.yang.m.service.LoginService;
import com.project.yang.m.utils.LogUtil;
import com.project.yang.m.utils.ToastUtil;
import com.project.yang.m.widget.CustomBottomDialog;
import com.project.yang.m.widget.CustomPopupWindow;
import com.project.yang.m.widget.WheelView;

import java.util.Arrays;

/**
 * Created by NiYang on 2017/3/28.
 */

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private ActivityTestBinding binding = null;
    private ActionBarDrawerToggle toggle = null;
    private String[] ITEMS = new String[]{"男","女"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_test, null, false);
        setContentView(this.binding.getRoot());

        this.binding.wheelView.setOffset(3);
        this.binding.wheelView.setItems(Arrays.asList(ITEMS));
        this.binding.wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                ToastUtil.showToast(selectedIndex + item);
            }
        });

        this.binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(TestActivity.this).inflate(R.layout.test_dialog_wheel_view, null);
                WheelView wheelView = (WheelView) view.findViewById(R.id.dialog_wheel_view);
                wheelView.setOffset(1);
                wheelView.setItems(Arrays.asList(new String[]{"你好","但是","是的"}));
                wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        ToastUtil.showToast(selectedIndex + item);
                    }
                });

                CustomBottomDialog customBottomDialog = new CustomBottomDialog(view, TestActivity.this,R.style.style_dialog);
                customBottomDialog.showDialog();

            }
        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        this.toggle = new ActionBarDrawerToggle(this, this.binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        this.binding.drawerLayout.setDrawerListener(this.toggle);
//        this.toggle.syncState();

//        LoginInfo loginInfo = new LoginInfo("1", "Jayce", "111111");
//        RetrofitHandler.getService(LoginService.class).login(loginInfo).subscribe(new DefaultSubscriber<UserAuth>() {
//            @Override
//            protected void onSuccess(UserAuth userAuth) {
//                System.out.println("================\n"+ userAuth.toString());
//            }
//
//            @Override
//            protected void onFail(Throwable e) {
//
//            }
//        });
//        RetrofitHandler.getService(LoginService.class).logout().subscribe(new DefaultSubscriber() {
//            @Override
//            protected void onSuccess(Object o) {
//                LogUtil.d(TAG,o.toString());
//            }
//
//            @Override
//            protected void onFail(Throwable e) {
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
