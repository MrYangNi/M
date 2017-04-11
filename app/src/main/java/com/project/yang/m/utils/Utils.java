package com.project.yang.m.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.project.yang.m.common.MApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

/**
 * @Author: NiYang
 * @Date: 2017/3/26.
 */
public class Utils {

    /**
     * 按钮左右摇摆动画
     */
    public static void buttonAnimation(Button button) {
        TranslateAnimation translateAnimation = new TranslateAnimation(10,-10 , 0, 0);
        translateAnimation.setInterpolator(new OvershootInterpolator());
        translateAnimation.setDuration(30);
        translateAnimation.setRepeatCount(4);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        button.startAnimation(translateAnimation);
    }

    /**
     * 把字符串解析成日期
     * @param str
     * @return
     */
    public static Long stringTransformDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = null;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            LogUtil.e("ParseException", e.getMessage());
        }finally {
            return date.getTime();
        }
    }

    /**
     * 权限判定
     * @return
     */
    public static boolean checkPermission(Activity activity) {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        } else if (ContextCompat.checkSelfPermission(MApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        } else if (ContextCompat.checkSelfPermission(MApplication.getContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.INTERNET);
        } else if (ContextCompat.checkSelfPermission(MApplication.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_PHONE_STATE);
        } else if (ContextCompat.checkSelfPermission(MApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else if (ContextCompat.checkSelfPermission(MApplication.getContext(), Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_WIFI_STATE);
        } else if (ContextCompat.checkSelfPermission(MApplication.getContext(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        } else if (ContextCompat.checkSelfPermission(MApplication.getContext(), Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CHANGE_WIFI_STATE);
        } else if (ContextCompat.checkSelfPermission(MApplication.getContext(), Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        }
        if (!permissions.isEmpty()) {
            String[] permission = permissions.toArray(new String[permissions.size()]);
            ActivityCompat.requestPermissions(activity, permission, 1);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取SHA1
     * @param context
     * @return
     */
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
