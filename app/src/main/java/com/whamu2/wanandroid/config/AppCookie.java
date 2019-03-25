package com.whamu2.wanandroid.config;

import com.whamu2.wanandroid.App;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author whamu2
 * @date 2018/7/12
 */
public class AppCookie implements CookieJar {

    private AppCookieManager manager = new AppCookieManager(App.getApplication());

    /**
     * 从请求结果的Header中Cookie做本地持久化
     *
     * @param url     {@link HttpUrl}
     * @param cookies Set-Cookie
     */
    @Override
    public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (int i = 0; i < cookies.size(); i++) {
                manager.addCookie(url, cookies.get(i));
            }
        }
    }

    /**
     * 向请求体头部注入以Cookie的Header
     *
     * @param url {@link HttpUrl}
     * @return Set-Cookie
     */
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
        return manager.loadCookies(url);
    }
}
