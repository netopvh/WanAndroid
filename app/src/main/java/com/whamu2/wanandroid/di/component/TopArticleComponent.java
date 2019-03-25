package com.whamu2.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.di.model.TopArticleModule;
import com.whamu2.wanandroid.mvp.ui.fragment.TopArticleFragment;

import dagger.Component;


@ActivityScope
@Component(modules = TopArticleModule.class, dependencies = AppComponent.class)
public interface TopArticleComponent {
    void inject(TopArticleFragment fragment);
}
