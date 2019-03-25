package com.whamu2.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.di.model.ProjectModule;
import com.whamu2.wanandroid.mvp.ui.fragment.ProjectFragment;

import dagger.Component;


@ActivityScope
@Component(modules = ProjectModule.class, dependencies = AppComponent.class)
public interface ProjectComponent {

    void inject(ProjectFragment fragment);
}
