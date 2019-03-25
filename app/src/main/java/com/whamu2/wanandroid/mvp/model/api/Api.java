package com.whamu2.wanandroid.mvp.model.api;

/**
 * @author whamu2
 * @date 2018/6/23
 */
public interface Api {
    String APP_DOMAIN = "https://www.wanandroid.com";

    /**
     * api接口
     *
     * @author whamu2
     * @date 2018/6/15
     */
    class Url {
        /**
         * 注册
         */
        public static final String REGISTER = "/user/register";
        /**
         * 登录
         */
        public static final String LOGIN = "/user/login";
        /**
         * 登出
         */
        public static final String LOGOUT = "/user/logout/json";
        /**
         * banner
         */
        public static final String BANNER = "banner/json";
        /**
         * 获取顶置文章
         */
        public static final String TOP = "article/top/json";
        /**
         * 获取文章列表
         */
        public static final String HOST_LIST = "/article/list/{page}/json";
        /**
         * 知识体系
         */
        public static final String TREE = "/tree/json";
        /**
         * 知识体系下对应文章
         */
        public static final String TREE_PAGE = "/article/list/{page}/json";
        /**
         * 搜索
         */
        public static final String SEARCH = "/article/query/{page}/json";
        /**
         * 项目目录
         */
        public static final String PROJECT = "project/tree/json";
        /**
         * 项目目录下文章
         */
        public static final String PROJECT_PAGE = "/project/list/{page}/json";
        /**
         * 公众号
         */
        public static final String SUBSCRIBE = "wxarticle/chapters/json";
        /**
         * 公众号下属文章
         */
        public static final String SUBSCRIBE_PAGE = "wxarticle/list/{id}/{page}/json";
        /**
         * 收藏列表
         */
        public static final String COLLECT_LIST = "lg/collect/list/{page}/json";
    }
}
