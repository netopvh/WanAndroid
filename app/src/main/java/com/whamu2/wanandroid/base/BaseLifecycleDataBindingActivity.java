package com.whamu2.wanandroid.base;

import android.app.Activity;
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
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.whamu2.android.PreferenceHelper;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.common.Container;
import com.whamu2.wanandroid.common.event.EventObj;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import static com.jess.arms.utils.ThirdViewUtil.convertAutoView;

/**
 * @author whamu2
 * @date 2018/6/13
 */
public abstract class BaseLifecycleDataBindingActivity<B extends ViewDataBinding, P extends IPresenter> extends AppCompatActivity
        implements IActivity, ActivityLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    private Handler mHandler;
    public B mViewBinding;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Inject
    @Nullable
    protected P mPresenter;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
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
            int layoutResID = initView(savedInstanceState);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    @Subscriber
    protected void onEventSubscribe(EventObj obj) {

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

    public Activity getActivity() {
        return this;
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
