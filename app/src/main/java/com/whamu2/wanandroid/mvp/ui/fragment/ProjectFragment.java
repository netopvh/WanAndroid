package com.whamu2.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseLifecycleDataBindingFragment;
import com.whamu2.wanandroid.databinding.FragmentProjectBinding;
import com.whamu2.wanandroid.di.component.DaggerProjectComponent;
import com.whamu2.wanandroid.di.model.ProjectModule;
import com.whamu2.wanandroid.mvp.contract.ProjectContract;
import com.whamu2.wanandroid.mvp.model.bean.Cycle;
import com.whamu2.wanandroid.mvp.presenter.ProjectPresenter;
import com.whamu2.wanandroid.mvp.ui.adapter.TabAdapter;

import java.util.List;

/**
 * 项目
 *
 * @author Suming
 * @date 2019/3/12
 * @address https://github.com/whamu2
 */
public class ProjectFragment extends BaseLifecycleDataBindingFragment<FragmentProjectBinding, ProjectPresenter> implements ProjectContract.View {
    private static final String TAG = ProjectFragment.class.getSimpleName();

    public static ProjectFragment newInstance() {
        Bundle args = new Bundle();
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerProjectComponent.builder()
                .appComponent(appComponent)
                .projectModule(new ProjectModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.getProjectList();
        }
    }

    @Override
    public void onProjectDone(List<Cycle> treeBeans) {
        TabAdapter adapter = new TabAdapter(getChildFragmentManager());
        for (Cycle bean : treeBeans) {
            adapter.addTab(ProjectChildFragment.newInstance(bean.getId()), bean.getName());
        }
        mViewBinding.viewpager.setAdapter(adapter);
        mViewBinding.tlBar.setupWithViewPager(mViewBinding.viewpager);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
}
