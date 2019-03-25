package com.whamu2.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.di.model.WechatModule;
import com.whamu2.wanandroid.mvp.ui.fragment.WechatFragment;

import dagger.Component;


@ActivityScope
@Component(modules = WechatModule.class, dependencies = AppComponent.class)
public interface WechatComponent {

    void inject(WechatFragment fragment);
}
