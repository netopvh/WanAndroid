package com.whamu2.wanandroid.mvp.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.tencent.smtt.sdk.WebView;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseDataBindingActivity;
import com.whamu2.wanandroid.databinding.ActivityCommonWebBinding;
import com.whamu2.wanandroid.widget.ScrollWebView;

/**
 * @author whamu2
 * @date 2018/7/5
 */
public class CommonWebActivity extends BaseDataBindingActivity<ActivityCommonWebBinding> implements ScrollWebView.OnWebViewPageLoadStateListener {
    private static final String TAG = CommonWebActivity.class.getSimpleName();
    private static final String KEY_URL = "URL";

    public static void start(Context context, String s) {
        Intent starter = new Intent(context, CommonWebActivity.class);
        starter.putExtra(KEY_URL, s);
        context.startActivity(starter);
    }

    @Override
    protected int initView() {
        return R.layout.activity_common_web;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setupToolbar(mViewBinding.toolbar, "");

        mViewBinding.webView.setTopProgressBarVisibility(true);
        mViewBinding.webView.setTopProgressBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        mViewBinding.webView.setOnWebViewPageLoadStateListener(this);
        if (getUrl() != null) {
            mViewBinding.webView.loadUrl(getUrl());
        }
    }

    private String getUrl() {
        if (getIntent() != null) {
            return getIntent().getStringExtra(KEY_URL);
        }
        return null;
    }

    @Override
    public void shouldOverrideUrlLoading(WebView view, String s) {

    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        String title = mViewBinding.webView.getTitle();
        if (!TextUtils.isEmpty(title)) {
            setupToolbar(mViewBinding.toolbar, title);
        }
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        String title = mViewBinding.webView.getTitle();
        if (!TextUtils.isEmpty(title)) {
            setupToolbar(mViewBinding.toolbar, title);
        }
    }
}
