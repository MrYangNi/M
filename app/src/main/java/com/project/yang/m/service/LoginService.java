package com.project.yang.m.service;

import com.project.yang.m.beans.LoginInfo;
import com.project.yang.m.beans.UserAuth;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @Author: NiYang
 * @Date: 2017/4/8.
 */
public interface LoginService {
    /**
     * 用户登录
     * @param loginInfo
     * @return
     */
    @POST("login")
    Observable<UserAuth> login(@Body LoginInfo loginInfo);

    /**
     * 用户注销
     * @return
     */
    @POST("logout")
    Observable<Object> logout();
}
