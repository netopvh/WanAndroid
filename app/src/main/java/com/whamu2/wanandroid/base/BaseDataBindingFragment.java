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

import com.whamu2.wanandroid.common.event.EventObj;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/**
 * @author whamu2
 * @date 2018/6/13
 */
public abstract class BaseDataBindingFragment<B extends ViewDataBinding> extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    private Handler mHandler;
    public B mViewBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(initView(), container, false);
        //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
        if (rootView != null) {
            mViewBinding = DataBindingUtil.bind(rootView);
        }
        initData(savedInstanceState);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        return rootView;
    }

    protected abstract int initView();

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    /**
     * 是否使用eventBus,默认为使用(true)，
     */
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


    @Subscriber
    protected void onEventSubscribe(EventObj obj) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

}
