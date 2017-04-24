package com.project.yang.m.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: NiYang
 * @Date: 2017/4/8.
 */
public class RetrofitHandler {
    private static Retrofit retrofit = null;
    private static Map<Class<?>, Object> serviceCache = new HashMap<>();
    private static String baseUrl = "http://120.24.246.150:8080/server/";

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpClientFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static class ThreadTransformer<T> implements Observable.Transformer<T, T> {
        @Override
        public Observable<T> call(Observable<T> tObservable) {
            return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    }

    public static <T> T getService(final Class<T> service) {
        T result = (T) serviceCache.get(service);
        if (result == null) {
            result = (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                    new InvocationHandler() {
                        T t = (T) retrofit.create(service);

                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            return ((Observable) Proxy.getInvocationHandler(t).invoke(t, method, args))
                                    .compose(new ThreadTransformer<T>()).flatMap(new Func1<T, Observable<T>>() {

                                        @Override
                                        public Observable<T> call(T t) {
                                            return Observable.just(t);
                                        }
                                    });
                        }
                    });
            serviceCache.put(service, result);
        }
        return result;
    }
}
