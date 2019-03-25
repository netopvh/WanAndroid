package com.whamu2.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.di.model.ProjectChildModule;
import com.whamu2.wanandroid.mvp.ui.fragment.ProjectChildFragment;

import dagger.Component;

/**
 * @author whamu2
 * @date 2018/7/3
 */

@ActivityScope
@Component(modules = ProjectChildModule.class, dependencies = AppComponent.class)
public interface ProjectChildComponent {
    void inject(ProjectChildFragment fragment);
}
