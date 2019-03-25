package com.whamu2.wanandroid.utils;

import com.whamu2.wanandroid.App;

/**
 * @author Suming
 * @date 2019/3/6
 * @address https://github.com/whamu2
 */
public class ApiUtil {
    private static volatile ApiUtil INSTANCE;

    private ApiUtil() {
    }

    public synchronized static ApiUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiUtil.class) {
                INSTANCE = new ApiUtil();
            }
        }
        return INSTANCE;
    }

    public <T> T create(Class<T> sClass) {
        return SingletonLoadHelper.register(App.getApplication(), sClass);
    }
}
