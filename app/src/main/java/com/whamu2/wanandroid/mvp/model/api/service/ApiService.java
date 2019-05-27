package com.whamu2.wanandroid.mvp.model.api.service;

import com.android.lib.apt.ApiFactory;
import com.whamu2.wanandroid.mvp.model.api.Api;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BannerBean;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Cycle;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;
import com.whamu2.wanandroid.mvp.model.bean.SearchData;
import com.whamu2.wanandroid.mvp.model.bean.User;
import com.whamu2.wanandroid.mvp.model.bean.WechatNumber;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Suming
 * @date 2019/3/11
 * @address https://github.com/whamu2
 */

@ApiFactory
public interface ApiService {

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 密码
     */
    @POST(Api.Url.REGISTER)
    @FormUrlEncoded
    Observable<BaseResp<User>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @POST(Api.Url.LOGIN)
    @FormUrlEncoded
    Observable<BaseResp<User>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 查Banner
     */
    @GET(Api.Url.BANNER)
    Observable<BaseResp<List<BannerBean>>> getBanner();

    /**
     * 获取顶置文章
     */
    @GET(Api.Url.TOP)
    Observable<BaseResp<List<Articles>>> getTopArticles();

    /**
     * 获取文章列表
     *
     * @param page 页码
     */
    @GET(Api.Url.HOST_LIST)
    Observable<BaseResp<Pagination>> getHotArticles(@Path("page") int page);

    /**
     * 搜索
     *
     * @param page    页码
     * @param keyword 关键字
     * @return {@link Observable}
     */
    @POST(Api.Url.SEARCH)
    @FormUrlEncoded
    Observable<BaseResp<SearchData>> search(@Path("page") int page, @Field("k") String keyword);

    /**
     * 知识体系
     */
    @GET(Api.Url.TREE)
    Observable<BaseResp<List<Cycle>>> getTreeList();

    /**
     * 知识体系下属文章
     *
     * @param page 页码
     * @param cid  知识体系ID
     */
    @GET(Api.Url.TREE_PAGE)
    Observable<BaseResp<Pagination>> getTreePageList(@Path("page") int page, @Query("cid") int cid);

    /**
     * 项目
     */
    @GET(Api.Url.PROJECT)
    Observable<BaseResp<List<Cycle>>> getProjectList();

    /**
     * 项目下属文章
     *
     * @param page 页码
     * @param cid  知识体系ID
     */
    @GET(Api.Url.PROJECT_PAGE)
    Observable<BaseResp<Pagination>> getProjectPageList(@Path("page") int page, @Query("cid") int cid);

    /**
     * 公众号
     */
    @GET(Api.Url.SUBSCRIBE)
    Observable<BaseResp<List<WechatNumber>>> getNumberList();

    /**
     * 公众号下属文章
     *
     * @param page 页码
     * @param id   公众号ID
     */
    @GET(Api.Url.SUBSCRIBE_PAGE)
    Observable<BaseResp<Pagination>> getNumberPageList(@Path("id") int id, @Path("page") int page);

    /**
     * 登出
     */
    @GET(Api.Url.LOGOUT)
    Observable<BaseResp<Object>> logout();

    /**
     * 收藏列表
     */
    @GET(Api.Url.COLLECT_LIST)
    Observable<BaseResp<Pagination>> getCollectList(@Path("page") int page);
}
