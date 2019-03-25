package com.whamu2.wanandroid.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.whamu2.wanandroid.common.event.EventObj;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * @author whamu2
 * @date 2018/6/13
 */
public abstract class BaseLifecycleDataBindingFragment<B extends ViewDataBinding, P extends IPresenter> extends Fragment
        implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    @Inject
    @Nullable
    protected P mPresenter;
    public B mViewBinding;
    private Handler mHandler;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }


    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = initView(inflater, container, savedInstanceState);
        if (rootView != null) {
            mViewBinding = DataBindingUtil.bind(rootView);
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    public void post(Runnable runnable) {
        getHandler().postDelayed(runnable, 50);
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Subscriber
    protected void onEventSubscribe(EventObj obj) {

    }
}
