package com.whamu2.wanandroid.utils.observer;


import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

/**
 * @author whamu2
 * @date 2018/6/21
 */

public abstract class BaseObserver<T> implements Observer<T>, Subscriber<T>, Disposable {
    private final AtomicReference<Disposable> s = new AtomicReference<>();

    @Override
    public final void onSubscribe(Disposable s) {
        doSubscribe(s);
        if (DisposableHelper.setOnce(this.s, s)) {
            onStart();
        }
    }

    /**
     * Called once the single upstream Disposable is set via onSubscribe.
     */
    protected void onStart() {

    }

    @Override
    public void onNext(T value) {
        doNext(value);
    }

    @Override
    public void onError(Throwable e) {
        doError(e);
    }

    @Override
    public void onComplete() {
        doComplete();
    }


    @Override
    public final boolean isDisposed() {
        return s.get() == DisposableHelper.DISPOSED;
    }

    @Override
    public final void dispose() {
        DisposableHelper.dispose(s);
    }

}
