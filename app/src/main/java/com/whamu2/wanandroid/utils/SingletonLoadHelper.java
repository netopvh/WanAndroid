package com.whamu2.wanandroid.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.whamu2.wanandroid.mvp.model.api.service.ApiService;

/**
 * 简洁网络请求
 *
 * @author whamu2
 * @date 2018/6/29
 */
public class SingletonLoadHelper {

    private SingletonLoadHelper() {
        throw new IllegalStateException("u can`t init me");
    }

    /**
     * 注册Retrofit代理接口
     *
     * @param context {@link Context}
     * @param service 接口
     * @param <T>     Observable
     */
    public static <T> T register(Context context, @NonNull Class<T> service) {
        AppComponent appComponent = ArmsUtils
                .obtainAppComponentFromContext(context.getApplicationContext());

        return appComponent
                .repositoryManager()
                .obtainRetrofitService(service);
    }

    /**
     * 注册Retrofit代理接口
     *
     * @param context {@link Context}
     * @return {@link ApiService]}
     */
    public static ApiService register(Context context) {
        return register(context, ApiService.class);
    }
}
