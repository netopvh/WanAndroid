package com.whamu2.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.di.model.UserModule;
import com.whamu2.wanandroid.mvp.ui.activity.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(modules = UserModule.class, dependencies = AppComponent.class)
public interface UserComponent {
    void inject(LoginActivity activity);
}
