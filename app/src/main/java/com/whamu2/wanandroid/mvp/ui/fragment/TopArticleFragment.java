package com.whamu2.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseLifecycleDataBindingFragment;
import com.whamu2.wanandroid.databinding.FragmentTopArticleBinding;
import com.whamu2.wanandroid.di.component.DaggerTopArticleComponent;
import com.whamu2.wanandroid.di.model.TopArticleModule;
import com.whamu2.wanandroid.mvp.contract.TopArticleContract;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BannerBean;
import com.whamu2.wanandroid.mvp.presenter.TopArticlePresenter;
import com.whamu2.wanandroid.mvp.ui.adapter.SubclassAdapter;
import com.whamu2.wanandroid.mvp.ui.webview.CommonWebActivity;
import com.whamu2.wanandroid.mvp.ui.webview.PageDetailsActivity;
import com.whamu2.wanandroid.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 顶置文章
 *
 * @author Suming
 * @date 2019/3/11
 * @address https://github.com/whamu2
 */
public class TopArticleFragment extends BaseLifecycleDataBindingFragment<FragmentTopArticleBinding, TopArticlePresenter>
        implements TopArticleContract.View, OnRefreshLoadMoreListener {
    private static final String TAG = TopArticleFragment.class.getSimpleName();

    private SubclassAdapter mAdapter;
    private int mPageIndex;

    public static TopArticleFragment newInstance() {
        Bundle args = new Bundle();
        TopArticleFragment fragment = new TopArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTopArticleComponent.builder()
                .topArticleModule(new TopArticleModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_article, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mViewBinding.setListener(this::onClick);

        mAdapter = SubclassAdapter.create();
        mViewBinding.refresh.setOnRefreshLoadMoreListener(this);
        mViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration decoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.divider)));
        mViewBinding.recyclerView.addItemDecoration(decoration);
        mViewBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mViewBinding.refresh.autoRefresh();

        if (mPresenter != null) {
            mPresenter.getBanner(true);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPageIndex = 0;
            mPresenter.getData(mPageIndex);
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            //mPageIndex++;
            mPresenter.getData(mPageIndex);
        }
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void onArticleDone(List<Articles> articles, int curPage) {
        mPageIndex = curPage;
        if (curPage == 1) {
            mAdapter.setNewData(articles);
        } else {
            mAdapter.addData(articles);
        }
    }

    @Override
    public void onBannerDone(List<BannerBean> banners) {
        View view = getLayoutInflater().inflate(R.layout.layout_banner, (ViewGroup) mViewBinding.recyclerView.getParent(), false);

        List<String> paths = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < banners.size(); i++) {
            paths.add(banners.get(i).getImagePath());
            titles.add(banners.get(i).getTitle());
        }

        Banner banner = (Banner) view;
        if (banner != null) {
            if (mAdapter != null) {
                mAdapter.removeAllHeaderView();
                mAdapter.addHeaderView(banner);
            }
            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(paths);
            banner.setBannerTitles(titles);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR_TITLE);
            //点击事件
            banner.setOnBannerListener(position -> CommonWebActivity.start(getContext(), paths.get(position)));
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mViewBinding.refresh.finishRefresh();
        mViewBinding.refresh.finishLoadMore();
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showLong(message);

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
    }

    @Override
    public void killMyself() {

    }

    private void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Articles item = mAdapter.getItem(position);

        PageDetailsActivity.start(getContext(), item);

    }

    public void onClick(View v) {
    }

}
