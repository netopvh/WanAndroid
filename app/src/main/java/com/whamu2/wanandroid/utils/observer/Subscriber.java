package com.whamu2.wanandroid.utils.observer;

import io.reactivex.disposables.Disposable;

/**
 * @author whamu2
 * @date 2018/6/21
 */
public interface Subscriber<T> {
    void doSubscribe(Disposable d);

    void doNext(T value);

    void doError(Throwable t);

    void doComplete();
}