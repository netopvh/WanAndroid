package com.whamu2.wanandroid.common;

import com.whamu2.wanandroid.App;
import com.whamu2.wanandroid.utils.MetaDataUtils;

/**
 * @author whamu2
 * @date 2018/6/14
 */
public class Container {

    public static class Const {
        /**
         * 默认页
         */
        public static final int DEFAULT_PAGE = 1;
        /**
         * 详情来源-收藏列表
         */
        public static final int ORIGIN_COLLECT_LIST = 800;
        /**
         * 详情来源-文章列表
         */
        public static final int ORIGIN_COLLECT_PAGE = 861;
    }

    public static class Key {
        /**
         * 保存用户信息到本地
         */
        public static final String KEY_LOCAL_USER = "local_user";
        /**
         * 夜间模式
         */
        public static final String KEY_NIGHT_MODE = "Night_Mode";

    }

    /**
     * 事件总线
     */
    public static class Event {

        public static final int LOGIN = 82;

        public static final int COLLECT_STATE_CHANGE = 214;
    }

    public static String getBuglyID() {
        return MetaDataUtils.getApplicationData(App.getApplication(), "MIT_BUGLY_APP_ID");
    }

    public static String getBuglyKey() {
        return MetaDataUtils.getApplicationData(App.getApplication(), "MIT_BUGLY_APP_KEY");
    }

    public static String getChannel() {
        return MetaDataUtils.getApplicationData(App.getApplication(), "CHANNEL");
    }
}