package com.whamu2.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.di.model.WechatChildModule;
import com.whamu2.wanandroid.mvp.ui.fragment.WechatChildFragment;

import dagger.Component;

/**
 * @author whamu2
 * @date 2018/7/3
 */

@ActivityScope
@Component(modules = WechatChildModule.class, dependencies = AppComponent.class)
public interface WechatChildComponent {
    void inject(WechatChildFragment fragment);
}
