package com.whamu2.wanandroid.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author whamu2
 * @date 2018/6/19
 */
public final class MetaDataUtils {

    /**
     * get activity component meta-data
     *
     * @param activity {@link Activity}
     * @param name     definition name
     * @return value
     */
    public static String getActivityData(Activity activity, @NonNull String name) {
        ActivityInfo info;
        try {
            info = activity.getPackageManager()
                    .getActivityInfo(activity.getComponentName(),
                            PackageManager.GET_META_DATA);
            return info.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get application component meta-data
     *
     * @param application {@link Application}
     * @param name        definition name
     * @return value
     */
    public static String getApplicationData(Application application, @NonNull String name) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = application.getPackageManager()
                    .getApplicationInfo(application.getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get service component meta-data
     *
     * @param sClazz {@link Service}
     * @param name   definition name
     * @return value
     */
    public static String getServiceData(Context context, @NonNull Class<Service> sClazz, @NonNull String name) {
        ComponentName cn = new ComponentName(context, sClazz);
        ServiceInfo info = null;
        try {
            info = context.getPackageManager()
                    .getServiceInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get application component meta-data
     *
     * @param ctx  {@link Context}
     * @param name definition name
     * @return value
     */
    public static String getAppMetaData(Context ctx, @NonNull String name) {
        if (ctx == null || TextUtils.isEmpty(name)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(name);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }
}
