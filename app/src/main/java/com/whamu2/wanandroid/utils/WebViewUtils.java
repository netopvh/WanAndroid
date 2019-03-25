package com.whamu2.wanandroid.utils;

import com.tencent.smtt.sdk.WebView;

/**
 * @author whamu2
 * @date 2018/7/5
 */
public final class WebViewUtils {


    /**
     * JS注入图片点击
     * <p>
     * <p>
     * readImageUrl(url) 读取图片地址加入集合
     * openImage(url) 显示图片
     * </p>
     *
     * @param webView {@link WebView}
     * @param element 事件标识
     */
    public static void addImageClickListener(WebView webView, String element) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{" +
                "window." + element + ".readImageUrl(objs[i].src);  " +
                " objs[i].onclick=function()  " +
                " {  " +
                " window." + element + ".openImage(this.src);  " +
                "  }  " +
                "}" +
                "})()");
    }
}
