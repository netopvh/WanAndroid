package com.whamu2.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.di.model.SubclassModule;
import com.whamu2.wanandroid.mvp.ui.fragment.SubclassFragment;

import dagger.Component;

/**
 * @author whamu2
 * @date 2018/7/3
 */

@ActivityScope
@Component(modules = SubclassModule.class, dependencies = AppComponent.class)
public interface SubclassComponent {
    void inject(SubclassFragment fragment);
}
