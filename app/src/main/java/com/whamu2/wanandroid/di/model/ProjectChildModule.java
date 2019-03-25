package com.whamu2.wanandroid.di.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.mvp.contract.ProjectChildCot;
import com.whamu2.wanandroid.mvp.model.ProjectChildModel;
import com.whamu2.wanandroid.mvp.ui.adapter.ProjectAdapter;

import java.util.Objects;

import dagger.Module;
import dagger.Provides;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@Module
public class ProjectChildModule {
    private ProjectChildCot.View view;

    public ProjectChildModule(ProjectChildCot.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProjectChildCot.View provideTreeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ProjectChildCot.Model provideTreeModel(ProjectChildModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    ProjectAdapter provideAdapter() {
        return ProjectAdapter.create();
    }

    @ActivityScope
    @Provides
    DividerItemDecoration provideDecoration() {
        Context context = view.getContext();
        DividerItemDecoration decoration = new DividerItemDecoration(Objects.requireNonNull(context), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.divider)));
        return decoration;
    }
}
