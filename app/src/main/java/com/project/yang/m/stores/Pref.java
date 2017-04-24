package com.project.yang.m.stores;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.project.yang.m.common.App;

/**
 * @Author: NiYang
 * @Date: 2017/4/8.
 */
public class Pref {
    private static final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(App.getContext());

    public static class Key {
        public static class Network {
            public static final String TOKEN = "token";
        }

        public static class User {
            public static final String USER_AUTH = "user_auth";
            public static final String USER_ID = "user_id";
            public static final String USER_NAME = "user_name";
            public static final String IS_LOGIN = "is_login";
        }
    }

    /**
     * 清空SharedPreferences中的值
     */
    public static void clearPrefData() {
        pref.edit().clear().commit();
    }

    /**
     * 存储[String,String]类型的键值对
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        pref.edit().putString(key, value).apply();
    }

    /**
     * 存储[String,Long]类型的键值对
     * @param key
     * @param value
     */
    public static void putLong(String key, Long value) {
        pref.edit().putLong(key,value).apply();
    }

    /**
     * 存储[String,Boolean]类型的键值对
     * @param key
     * @param value
     */
    public static void putBoolean(String key, Boolean value) {
        pref.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取String类型的值
     * @param key
     * @param def
     * @return
     */
    public static String getString(String key, String def) {
        return pref.getString(key, def);
    }

    /**
     * 获取Long类型的值
     * @param key
     * @param def
     * @return
     */
    public static Long getLong(String key, Long def) {
        return pref.getLong(key, def);
    }

    /**
     * 获取Boolean类型的值
     * @param key
     * @param def
     * @return
     */
    public static Boolean getBoolean(String key, Boolean def) {
        return pref.getBoolean(key, def);
    }
}
