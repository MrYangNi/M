package com.project.yang.m.network;

import rx.Subscriber;

/**
 * @Author: NiYang
 * @Date: 2017/4/8.
 */
public abstract class DefaultSubscriber<T> extends Subscriber<T> {
    private static final String TAG = "HttpException";

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {
        RetrofitException.httpException(e);
        onFail(e);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    protected abstract void onFail(Throwable e);

    protected abstract void onSuccess(T t);
}
