package com.project.yang.m.stores;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.project.yang.m.common.MApplication;

/**
 * @Author: NiYang
 * @Date: 2017/4/8.
 */
public class Pref {
    private static final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MApplication.getContext());

    public static class Key {
        public static class Network {
            public static final String TOKEN = "token";
        }

        public static class User {
            public static final String USER_AUTH = "user_auth";
            public static final String USER_ID = "user_id";
        }
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
}
