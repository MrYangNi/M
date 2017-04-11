package com.project.yang.m.network;

import okhttp3.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * OkHttpClient 生成器
 * @Author: NiYang
 * @Date: 2017/4/8.
 */
public class OkHttpClientFactory {

    private final static int connectionTimeout = 10;
    private final static int readTimeout = 30;

    public static OkHttpClient create() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        //频繁网络请求需要加入
                        .addHeader("Connection", "close")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .addHeader("Authorization", AuthManager.getAuthorization())
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        }).connectTimeout(connectionTimeout, TimeUnit.SECONDS)// 连接超时
                .readTimeout(readTimeout, TimeUnit.SECONDS)// 读取超时
                .build();
        return okHttpClient;
    }
}
