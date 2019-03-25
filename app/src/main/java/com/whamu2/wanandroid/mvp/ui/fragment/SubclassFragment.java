package com.whamu2.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseLifecycleDataBindingFragment;
import com.whamu2.wanandroid.common.Container;
import com.whamu2.wanandroid.databinding.FragmentSubclassBinding;
import com.whamu2.wanandroid.di.component.DaggerSubclassComponent;
import com.whamu2.wanandroid.di.model.SubclassModule;
import com.whamu2.wanandroid.mvp.contract.SubclassContract;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;
import com.whamu2.wanandroid.mvp.presenter.SubclassPresenter;
import com.whamu2.wanandroid.mvp.ui.adapter.SubclassAdapter;
import com.whamu2.wanandroid.mvp.ui.webview.PageDetailsActivity;
import com.whamu2.wanandroid.utils.GeneralUtil;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

/**
 * @author whamu2
 * @date 2018/7/3
 */
public class SubclassFragment extends BaseLifecycleDataBindingFragment<FragmentSubclassBinding, SubclassPresenter>
        implements SubclassContract.View, OnRefreshLoadMoreListener {
    private static final String TAG = SubclassFragment.class.getSimpleName();
    private static final String KEY_ID = "id";

    @Inject
    SubclassAdapter mAdapter;
    @Inject
    DividerItemDecoration mItemDecoration;

    private int mPageIndex;
    private int id;

    public static SubclassFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID, id);
        SubclassFragment fragment = new SubclassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSubclassComponent.builder()
                .appComponent(appComponent)
                .subclassModule(new SubclassModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subclass, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        id = Objects.requireNonNull(getArguments()).getInt(KEY_ID);
        mViewBinding.srl.setOnRefreshLoadMoreListener(this);
        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewBinding.rv.addItemDecoration(mItemDecoration);
        mViewBinding.rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mViewBinding.srl.autoRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPageIndex = 0;
            mPresenter.getListById(id, mPageIndex);
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            //mPageIndex++;
            mPresenter.getListById(id, mPageIndex);
        }
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void loadSuccess(List<Articles> articles, int curPage) {
        mPageIndex = curPage;
        if (curPage == 1) {
            mAdapter.setNewData(articles);
        } else {
            mAdapter.addData(articles);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showLong(message);

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        ActivityUtils.startActivity(intent);

    }

    @Override
    public void killMyself() {

    }

    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Articles item = mAdapter.getItem(position);

        PageDetailsActivity.start(getContext(), item);
    }
}
