package com.whamu2.wanandroid.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author whamu2
 * @date 2018/6/20
 */
public class ScrollWebView extends WebView {
    private static final String TAG = ScrollWebView.class.getSimpleName();

    /**
     * 手势状态
     */
    public enum State {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    /**
     * 滑动状态回掉
     */
    public interface OnScrollStateListener {
        void onScrollState(State state, int l, int t, int oldl, int oldt);
    }

    /**
     * WebViewClient方法回掉
     */
    public interface OnWebViewPageLoadStateListener {
        /**
         * 重载Url
         *
         * @param view {@link WebView}
         * @param s    url
         */
        void shouldOverrideUrlLoading(WebView view, String s);

        /**
         * H5开始加载
         *
         * @param webView {@link WebView}
         * @param s       url
         * @param bitmap
         */
        void onPageStarted(WebView webView, String s, Bitmap bitmap);

        /**
         * H5,JS加载完成
         *
         * @param webView {@link WebView}
         * @param s url
         */
        void onPageFinished(WebView webView, String s);
    }

    private OnScrollStateListener mOnScrollStateListener;
    private OnWebViewPageLoadStateListener mWebViewPageLoadStateListener;
    private ProgressBarView mProgressBarView;//进度条

    public ScrollWebView(Context context) {
        this(context, null);
        setBackgroundColor(85621);
    }

    public ScrollWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScrollWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.setWebViewClient(mWebViewClient);
        this.setWebChromeClient(mWebChromeClient);
        initWebViewSettings();
        this.getView().setClickable(true);
        mProgressBarView = new ProgressBarView(getContext());
        mProgressBarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(context, 2)));
        mProgressBarView.setProgress(1);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setGeolocationEnabled(true);
        // 缓存
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true); //开启DOM缓存，关闭的话H5自身的一些操作是无效的
        if (NetworkUtils.isConnected()) {
            webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSetting.setCacheMode(WebSettings.LOAD_CACHE_ONLY); //不使用网络，只加载缓存
        }
        webSetting.setLoadsImagesAutomatically(true); // 网页加载完成过后在加载图片
//        webSetting.setAppCachePath(cachePath);
//        webSetting.setAppCacheMaxSize(cacheSize);

    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (mWebViewPageLoadStateListener != null) {
                mWebViewPageLoadStateListener.shouldOverrideUrlLoading(view, url);
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            if (mWebViewPageLoadStateListener != null) {
                mWebViewPageLoadStateListener.onPageStarted(webView, s, bitmap);
            }
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            if (mWebViewPageLoadStateListener != null) {
                mWebViewPageLoadStateListener.onPageFinished(webView, s);
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            return super.shouldInterceptRequest(webView, s);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
//            String method = webResourceRequest.getMethod();
//            Uri url = webResourceRequest.getUrl();
//            Map<String, String> requestHeaders = webResourceRequest.getRequestHeaders();
//
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("method = ").append(method).append("\n");
//            stringBuilder.append("url = ").append(url).append("\n");
//            Set<Map.Entry<String, String>> entries = requestHeaders.entrySet();
//            for (Map.Entry<String, String> next : entries) {
//                stringBuilder.append("Key = ").append(next.getKey()).append(" : ").append("Value = ").append(next.getValue()).append("\n");
//            }
//            LogUtils.e(TAG, stringBuilder.toString());
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int newProgress) {
            super.onProgressChanged(webView, newProgress);
            if (newProgress == 100) {
                mProgressBarView.setVisibility(View.GONE);
            } else {
                mProgressBarView.setProgress(newProgress);
            }
        }
    };

    public void setTopProgressBarVisibility(boolean visibility) {
        if (visibility) {
            addView(mProgressBarView);
        }
    }

    public void setTopProgressBarColor(@ColorInt int color) {
        mProgressBarView.setColor(color);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        int dx = l - oldl; // x轴变化值
        int dy = t - oldt; // y轴变化值
        if (mOnScrollStateListener != null) {
            if (dy > 0) {
                mOnScrollStateListener.onScrollState(State.UP, l, t, oldl, oldt);
            }
            if (dy < 0) {
                mOnScrollStateListener.onScrollState(State.DOWN, l, t, oldl, oldt);
            }
            if (dy == 0 || dx == 0) {
                mOnScrollStateListener.onScrollState(State.NONE, l, t, oldl, oldt);
            }
            if (dx > 0) {
                mOnScrollStateListener.onScrollState(State.LEFT, l, t, oldl, oldt);
            }
            if (dx < 0) {
                mOnScrollStateListener.onScrollState(State.RIGHT, l, t, oldl, oldt);
            }
        }
    }

    public void setOnScrollStateListener(OnScrollStateListener listener) {
        this.mOnScrollStateListener = listener;
    }

    public void setOnWebViewPageLoadStateListener(OnWebViewPageLoadStateListener listener) {
        this.mWebViewPageLoadStateListener = listener;
    }

    private static int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
