package com.whamu2.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.di.model.TreeModule;
import com.whamu2.wanandroid.mvp.ui.fragment.SystemFragment;

import dagger.Component;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@ActivityScope
@Component(modules = TreeModule.class, dependencies = AppComponent.class)
public interface TreeComponent {
    void inject(SystemFragment fragment);
}
