package com.whamu2.wanandroid.utils.exception;

import android.util.Log;

/**
 * @author Eric
 * @date 2019/4/4
 * @github https://github.com/whamu2
 */
public class ServiceNotFoundException extends RuntimeException {

    private static final boolean DEBUG = true;

    public ServiceNotFoundException() {
        this("Service 不能为空!");
    }

    public ServiceNotFoundException(String message) {
        super(message);
        if (DEBUG) {
            Log.e(ServiceNotFoundException.class.getSimpleName(), message);
        }
    }
}
