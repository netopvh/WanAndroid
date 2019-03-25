package com.whamu2.wanandroid.utils.observer;

/**
 * @author whamu2
 * @date 2018/6/21
 */
public interface BaseCallback<T> {
    void onStart();

    void onCompleted();

    void onError(Throwable e);

    void onNext(T t);
}