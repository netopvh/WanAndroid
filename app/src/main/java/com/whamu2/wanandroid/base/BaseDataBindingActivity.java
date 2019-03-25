package com.whamu2.wanandroid.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.KeyboardUtils;
import com.whamu2.android.PreferenceHelper;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.common.Container;
import com.whamu2.wanandroid.common.event.EventObj;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import static com.jess.arms.utils.ThirdViewUtil.convertAutoView;

/**
 * @author whamu2
 * @date 2018/6/13
 */
public abstract class BaseDataBindingActivity<B extends ViewDataBinding> extends AppCompatActivity {
    private Handler mHandler;
    public B mViewBinding;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = initView();
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                View rootView = getLayoutInflater().inflate(layoutResID, null, false);
                mViewBinding = DataBindingUtil.bind(rootView);
                setContentView(rootView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        initData(savedInstanceState);
    }

    protected abstract int initView();

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    public boolean useEventBus() {
        return true;
    }

    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    /**
     * 设置{@link Toolbar}样式
     * 1、现在只有简单的标题样式，如果需要复杂的需求请自行扩展此方法
     *
     * @param toolbar {@link Toolbar}
     * @param title   标题
     */
    public void setupToolbar(@NonNull Toolbar toolbar, @Nullable CharSequence title) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            KeyboardUtils.hideSoftInput(this);
        });
    }

    @Subscriber
    protected void onEventSubscribe(EventObj obj) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 切换主题
     */
    public void reload() {
        boolean isNight = PreferenceHelper.getInstance(this).getBoolean(Container.Key.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }
}
