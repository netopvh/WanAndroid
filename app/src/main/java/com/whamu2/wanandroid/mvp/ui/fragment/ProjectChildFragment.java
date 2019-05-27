package com.whamu2.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.whamu2.wanandroid.common.Container;
import com.whamu2.wanandroid.databinding.FragmentProjectChildBinding;
import com.whamu2.wanandroid.di.component.DaggerProjectChildComponent;
import com.whamu2.wanandroid.di.model.ProjectChildModule;
import com.whamu2.wanandroid.mvp.contract.ProjectChildCot;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.presenter.ProjectChildPresenter;
import com.whamu2.wanandroid.mvp.ui.adapter.ProjectAdapter;
import com.whamu2.wanandroid.mvp.ui.webview.PageDetailsActivity;
import com.whamu2.wanandroid.utils.GeneralUtil;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

/**
 * @author Suming
 * @date 2019/3/12
 * @address https://github.com/whamu2
 */
public class ProjectChildFragment extends BaseLifecycleDataBindingFragment<FragmentProjectChildBinding, ProjectChildPresenter>
        implements ProjectChildCot.View, OnRefreshLoadMoreListener {
    private static final String TAG = ProjectChildFragment.class.getSimpleName();
    private static final String KEY_ID = "ID";

    @Inject
    DividerItemDecoration mItemDecoration;
    @Inject
    ProjectAdapter mAdapter;

    private int mPageIndex;
    private int cid;

    public static ProjectChildFragment newInstance(int cid) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID, cid);
        ProjectChildFragment fragment = new ProjectChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerProjectChildComponent.builder()
                .appComponent(appComponent)
                .projectChildModule(new ProjectChildModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_child, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        cid = Objects.requireNonNull(getArguments()).getInt(KEY_ID);
        mViewBinding.refresh.setOnRefreshLoadMoreListener(this);
        mViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewBinding.recyclerView.addItemDecoration(mItemDecoration);
        mViewBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mViewBinding.refresh.autoRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPageIndex = Container.Const.DEFAULT_PAGE;
            mPresenter.getListById(cid, mPageIndex);
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPageIndex++;
            mPresenter.getListById(cid, mPageIndex);
        }
    }

    @Override
    public void onDataDone(List<Articles> articles, int curPage) {
        mPageIndex = curPage;
        if (GeneralUtil.isFirstPage(curPage)) {
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
        mViewBinding.refresh.finishLoadMore();
        mViewBinding.refresh.finishRefresh();
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
}
