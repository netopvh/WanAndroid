package com.whamu2.wanandroid.utils.observer;

import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandlerFactory;

/**
 * @author whamu2
 * @date 2018/6/21
 */

public class CommonSubscriber<T> extends BaseObserver<T> {
    private ErrorHandlerFactory mHandlerFactory;

    private Callback<T> callback;

    public CommonSubscriber(Callback<T> callback) {
        this.callback = callback;
    }

    public CommonSubscriber(RxErrorHandler rxErrorHandler, Callback<T> callback) {
        this.callback = callback;
        this.mHandlerFactory = rxErrorHandler.getHandlerFactory();
    }

    @Override
    public void onStart() {
        super.onStart();
        callback.onStart();
    }

    @Override
    public void doSubscribe(Disposable d) {

    }

    @Override
    public void doNext(T value) {
        callback.onNext(value);
    }

    @Override
    public void doError(Throwable t) {
        t.printStackTrace();
        //如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉
        //如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
        if (mHandlerFactory != null) {
            mHandlerFactory.handleError(t);
        }
        callback.onError(t);
    }

    @Override
    public void doComplete() {
        callback.onCompleted();
    }
}