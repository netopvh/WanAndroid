package com.whamu2.wanandroid.di.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.mvp.contract.FavoritesCot;
import com.whamu2.wanandroid.mvp.model.FavoritesModel;
import com.whamu2.wanandroid.mvp.ui.adapter.FavoritesAdapter;

import java.util.Objects;

import dagger.Module;
import dagger.Provides;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@Module
public class FavoritesModule {
    private FavoritesCot.View view;

    public FavoritesModule(FavoritesCot.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FavoritesCot.View provideTreeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FavoritesCot.Model provideTreeModel(FavoritesModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    FavoritesAdapter provideAdapter() {
        return FavoritesAdapter.create();
    }

    @ActivityScope
    @Provides
    DividerItemDecoration provideDecoration() {
        Context context = view.getActivity();
        DividerItemDecoration decoration = new DividerItemDecoration(Objects.requireNonNull(context), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.divider)));
        return decoration;
    }
}
