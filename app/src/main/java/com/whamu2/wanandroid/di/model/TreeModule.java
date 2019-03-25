package com.whamu2.wanandroid.di.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.mvp.contract.TreeContract;
import com.whamu2.wanandroid.mvp.model.TreeModel;
import com.whamu2.wanandroid.mvp.ui.adapter.SystemAdapter;

import java.util.Objects;

import dagger.Module;
import dagger.Provides;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@Module
public class TreeModule {
    private TreeContract.View view;

    public TreeModule(TreeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TreeContract.View provideTreeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TreeContract.Model provideTreeModel(TreeModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    SystemAdapter provideAdapter() {
        return SystemAdapter.create();
    }

    @ActivityScope
    @Provides
    LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getFragment().getContext());
    }

    @ActivityScope
    @Provides
    DividerItemDecoration provideDecoration() {
        Context context = view.getFragment().getContext();
        DividerItemDecoration decoration = new DividerItemDecoration(Objects.requireNonNull(context), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.divider)));
        return decoration;
    }

}
