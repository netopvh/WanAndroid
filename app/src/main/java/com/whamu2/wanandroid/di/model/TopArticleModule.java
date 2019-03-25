package com.whamu2.wanandroid.di.model;

import android.support.v7.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.mvp.contract.TopArticleContract;
import com.whamu2.wanandroid.mvp.model.TopArticleModel;
import com.whamu2.wanandroid.mvp.ui.adapter.SubclassAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@Module
public class TopArticleModule {
    private TopArticleContract.View view;

    public TopArticleModule(TopArticleContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TopArticleContract.View provideHomePageView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TopArticleContract.Model provideHomePageModel(TopArticleModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getFragment().getContext());
    }

    @ActivityScope
    @Provides
    SubclassAdapter provideAdapter() {
        return SubclassAdapter.create();
    }
}
