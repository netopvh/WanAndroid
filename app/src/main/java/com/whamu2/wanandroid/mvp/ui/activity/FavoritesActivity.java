package com.whamu2.wanandroid.mvp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseLifecycleDataBindingActivity;
import com.whamu2.wanandroid.common.Container;
import com.whamu2.wanandroid.databinding.ActivityCollectBinding;
import com.whamu2.wanandroid.di.component.DaggerFavoritesComponent;
import com.whamu2.wanandroid.di.model.FavoritesModule;
import com.whamu2.wanandroid.mvp.contract.FavoritesCot;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.presenter.FavoritesPresenter;
import com.whamu2.wanandroid.mvp.ui.adapter.FavoritesAdapter;
import com.whamu2.wanandroid.mvp.ui.webview.PageDetailsActivity;
import com.whamu2.wanandroid.utils.GeneralUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * @author suming
 * @date 15:39 2019 May Mon
 * @des https://github.com/whamu2
 */
public class FavoritesActivity extends BaseLifecycleDataBindingActivity<ActivityCollectBinding, FavoritesPresenter>
        implements FavoritesCot.View, OnRefreshLoadMoreListener {
    @Inject
    DividerItemDecoration mItemDecoration;
    @Inject
    FavoritesAdapter mAdapter;

    private int mPageIndex;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFavoritesComponent.builder()
                .appComponent(appComponent)
                .favoritesModule(new FavoritesModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_collect;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setupToolbar(mViewBinding.toolbar, "我的收藏");

        mViewBinding.refresh.setOnRefreshLoadMoreListener(this);
        mViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.recyclerView.addItemDecoration(mItemDecoration);
        mViewBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mViewBinding.refresh.autoRefresh();
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
            mPageIndex++;
            mPresenter.getData(mPageIndex);
        }
    }

    @Override
    public void onDataDone(List<Articles> data, int curPage) {
        mPageIndex = curPage;
        if (GeneralUtil.isFirstPage(curPage)) {
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mViewBinding.refresh.finishLoadMore();
        mViewBinding.refresh.finishRefresh();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showLong(message);
    }

    private void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Articles item = mAdapter.getItem(position);
        PageDetailsActivity.start(this, item);
    }
}
