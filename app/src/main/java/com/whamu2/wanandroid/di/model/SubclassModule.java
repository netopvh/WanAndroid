package com.whamu2.wanandroid.di.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.mvp.contract.SubclassContract;
import com.whamu2.wanandroid.mvp.model.SubclassModel;
import com.whamu2.wanandroid.mvp.ui.adapter.SubclassAdapter;

import java.util.Objects;

import dagger.Module;
import dagger.Provides;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@Module
public class SubclassModule {
    private SubclassContract.View view;

    public SubclassModule(SubclassContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SubclassContract.View provideTreeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SubclassContract.Model provideTreeModel(SubclassModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getFragment().getContext());
    }

    @ActivityScope
    @Provides
    SubclassAdapter provideSubclassAdapter() {
        return SubclassAdapter.create();
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
