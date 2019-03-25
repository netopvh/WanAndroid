package com.whamu2.wanandroid.di.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.mvp.contract.WechatChildCot;
import com.whamu2.wanandroid.mvp.model.WechatChildModel;
import com.whamu2.wanandroid.mvp.ui.adapter.SubclassAdapter;

import java.util.Objects;

import dagger.Module;
import dagger.Provides;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@Module
public class WechatChildModule {
    private WechatChildCot.View view;

    public WechatChildModule(WechatChildCot.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WechatChildCot.View provideTreeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WechatChildCot.Model provideTreeModel(WechatChildModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    SubclassAdapter provideAdapter() {
        return SubclassAdapter.create();
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
