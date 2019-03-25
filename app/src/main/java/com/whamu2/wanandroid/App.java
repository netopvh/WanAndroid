package com.whamu2.wanandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.jess.arms.base.BaseApplication;
import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.whamu2.wanandroid.utils.MetaDataUtils;
import com.whamu2.wanandroid.utils.database.DatabaseManager;

import java.util.Stack;

/**
 * @author whamu2
 * @date 2018/6/23
 */
public class App extends BaseApplication {
    private static final String TAG = App.class.getSimpleName();

    private static volatile App sApplication;

    public Stack<Activity> store;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        Utils.init(this);
        store = new Stack<>();

        initStetho();
        DatabaseManager.getInstance().init(this);
        initBugly();
        initX5();
        initLeakCanary();
        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }

    private void initBugly() {
        String mit_bugly_app_id = MetaDataUtils.getApplicationData(this, "MIT_BUGLY_APP_ID");
        ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(this);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppVersion(AppUtils.getAppVersionName());
        strategy.setAppPackageName(AppUtils.getAppPackageName());
        if (channelInfo != null) {
            String channel = channelInfo.getChannel();
            strategy.setAppChannel(channel);
        }
        CrashReport.initCrashReport(getApplicationContext(), mit_bugly_app_id, false, strategy);
        Bugly.init(getApplicationContext(), mit_bugly_app_id, false);

    }

    private void initX5() {
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }
        });
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static App getApplication() {
        return sApplication;
    }

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            return new MaterialHeader(context); //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new BallPulseFooter(context));
    }

    private class SwitchBackgroundCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            store.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            store.remove(activity);
        }
    }

    /**
     * 获取当前的Activity
     *
     * @return 当前的Activity
     */
    public Activity getCurActivity() {
        return store.lastElement();
    }
}
