package com.whamu2.wanandroid.mvp.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.WebView;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseDataBindingActivity;
import com.whamu2.wanandroid.common.event.EventObj;
import com.whamu2.wanandroid.databinding.ActivityWebviewPageBinding;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.ui.dialog.MoreBottomDialog;
import com.whamu2.wanandroid.widget.ScrollWebView;

import static com.whamu2.wanandroid.common.Container.Event.COLLECT_STATE_CHANGE;

/**
 * WebView显示文章详情
 *
 * @author whamu2
 * @date 2018/7/4
 */
public class PageDetailsActivity extends BaseDataBindingActivity<ActivityWebviewPageBinding>
        implements ScrollWebView.OnWebViewPageLoadStateListener, ScrollWebView.OnScrollStateListener {
    private static final String TAG = PageDetailsActivity.class.getSimpleName();
    public static final String KEY_DATA = "data";
    private static final String KEY_ORIGIN = "origin";

    private Articles mArticles;
    private int origin;

    public static void start(Context context,
                             Articles bean, int origin) {
        Intent starter = new Intent(context, PageDetailsActivity.class);
        starter.putExtra(KEY_DATA, bean);
        starter.putExtra(KEY_ORIGIN, origin);
        context.startActivity(starter);
    }

    @Override
    protected int initView() {
        return R.layout.activity_webview_page;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null) {
            mArticles = getIntent().getParcelableExtra(KEY_DATA);
            origin = getIntent().getIntExtra(KEY_ORIGIN, -1);
        }
        String title = mArticles != null ? mArticles.getTitle() : "文章详情页";
        Toolbar toolbar = mViewBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            KeyboardUtils.hideSoftInput(this);
        });

        mViewBinding.webView.setTopProgressBarVisibility(true);
        mViewBinding.webView.setTopProgressBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        mViewBinding.webView.setOnWebViewPageLoadStateListener(this);
        mViewBinding.webView.setOnScrollStateListener(this);
        mViewBinding.webView.addJavascriptInterface(new JavascriptObject(this), "");

        if (mArticles != null) {
            mViewBinding.webView.loadUrl(mArticles.getLink());
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
        if (itemId == R.id.action_more) {
            MoreBottomDialog.newInstance(mArticles, origin)
                    .show(getSupportFragmentManager(), "MoreBottomDialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onEventSubscribe(EventObj obj) {
        super.onEventSubscribe(obj);
        if (obj.getKey() == COLLECT_STATE_CHANGE) {
            boolean isCollect = (boolean) obj.getValue();
            mArticles.setCollect(isCollect);
        }
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

    @Override
    public void onScrollState(ScrollWebView.State state, int l, int t, int oldl, int oldt) {

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
