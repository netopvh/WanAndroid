package com.whamu2.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.di.model.FavoritesModule;
import com.whamu2.wanandroid.mvp.ui.activity.FavoritesActivity;

import dagger.Component;

/**
 * @author whamu2
 * @date 2018/7/3
 */

@ActivityScope
@Component(modules = FavoritesModule.class, dependencies = AppComponent.class)
public interface FavoritesComponent {
    void inject(FavoritesActivity activity);
}
