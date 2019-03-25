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

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseLifecycleDataBindingFragment;
import com.whamu2.wanandroid.databinding.FragmentSystemBinding;
import com.whamu2.wanandroid.di.component.DaggerTreeComponent;
import com.whamu2.wanandroid.di.model.TreeModule;
import com.whamu2.wanandroid.mvp.contract.TreeContract;
import com.whamu2.wanandroid.mvp.model.bean.Cycle;
import com.whamu2.wanandroid.mvp.presenter.TreePresenter;
import com.whamu2.wanandroid.mvp.ui.activity.SystemActivity;
import com.whamu2.wanandroid.mvp.ui.adapter.SystemAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Suming
 * @date 2019/3/12
 * @address https://github.com/whamu2
 */
public class SystemFragment extends BaseLifecycleDataBindingFragment<FragmentSystemBinding, TreePresenter> implements TreeContract.View {
    private static final String TAG = SystemFragment.class.getSimpleName();


    private SystemAdapter mAdapter;
    @Inject
    DividerItemDecoration mDecoration;

    public static SystemFragment newInstance() {
        Bundle args = new Bundle();
        SystemFragment fragment = new SystemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTreeComponent.builder()
                .appComponent(appComponent)
                .treeModule(new TreeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_system, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mViewBinding.toolbar.setTitle(R.string.title_system);
        mViewBinding.refresh.setOnRefreshListener(this::onRefresh);
        mViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewBinding.recyclerView.addItemDecoration(mDecoration);
        mAdapter = SystemAdapter.create();
        mViewBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mViewBinding.refresh.autoRefresh();
    }

    private void onRefresh(RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPresenter.getTree(true);
        }
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void loadTreeSuc(List<Cycle> treeBeans) {
        mAdapter.setNewData(treeBeans);
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
        Cycle item = mAdapter.getItem(position);
        post(() -> SystemActivity.start(getActivity(), item));
    }
}
