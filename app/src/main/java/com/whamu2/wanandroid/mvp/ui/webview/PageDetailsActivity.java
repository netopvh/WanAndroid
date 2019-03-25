package com.whamu2.wanandroid.mvp.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.WebView;
import com.whamu2.wanandroid.App;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseDataBindingActivity;
import com.whamu2.wanandroid.databinding.ActivityWebviewPageBinding;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.widget.ScrollWebView;

import java.util.Objects;

/**
 * WebView显示文章详情
 *
 * @author whamu2
 * @date 2018/7/4
 */
public class PageDetailsActivity extends BaseDataBindingActivity<ActivityWebviewPageBinding> implements ScrollWebView.OnWebViewPageLoadStateListener {
    private static final String TAG = PageDetailsActivity.class.getSimpleName();
    public static final String KEY_DATA = "data";

    public static void start(Context context, Articles bean) {
        Intent starter = new Intent(context, PageDetailsActivity.class);
        starter.putExtra(KEY_DATA, bean);
        context.startActivity(starter);
    }


    @Override
    protected int initView() {
        return R.layout.activity_webview_page;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setupToolbar(mViewBinding.toolbar, getArticle() != null ? getArticle().getTitle() : "");

        mViewBinding.webView.setTopProgressBarVisibility(true);
        mViewBinding.webView.setTopProgressBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        mViewBinding.webView.setOnWebViewPageLoadStateListener(this);
        mViewBinding.webView.addJavascriptInterface(new JavascriptObject(this), "");
        mViewBinding.setListener(this::onClick);

        if (getArticle() != null) {
            mViewBinding.webView.loadUrl(getArticle().getLink());
            goCollect(getArticle().isCollect());
        }


    }

    private Articles getArticle() {
        if (getIntent() != null) {
            return getIntent().getParcelableExtra(KEY_DATA);
        }
        return null;
    }

    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (getArticle() == null) {
                    return;
                }
                boolean collect = getArticle().isCollect();
                if (collect) {
                    goCollect(false);
                } else {
                    goCollect(true);
                    Snackbar.make(view, "收藏成功", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
                break;
        }
    }


    private void goCollect(boolean val) {
        if (val) {
            mViewBinding.fab.setImageTintList(
                    ContextCompat.getColorStateList(App.getApplication(), R.color.colorAccent));
        } else {
            mViewBinding.fab.setImageTintList(
                    ContextCompat.getColorStateList(App.getApplication(), R.color.gray));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_browser) {
            Uri uri = Uri.parse(Objects.requireNonNull(getArticle()).getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_link) {
            if (getArticle() != null && !TextUtils.isEmpty(getArticle().getProjectLink())) {
                Uri uri = Uri.parse(getArticle().getProjectLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void shouldOverrideUrlLoading(WebView view, String s) {

    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {

    }

    @Override
    public void onPageFinished(WebView webView, String s) {

    }


    private static class JavascriptObject {
        private Context mContext;

        JavascriptObject(Context mContext) {
            this.mContext = mContext;
        }

        @JavascriptInterface
        public void readImageUrl(String url) {

        }

        @JavascriptInterface
        public void openImage(String url) {
            ToastUtils.showLong(url);
        }
    }
}
