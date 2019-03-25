package com.whamu2.wanandroid.di.model;

import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.mvp.contract.UserContract;
import com.whamu2.wanandroid.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class UserModule {
    private UserContract.View view;

    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view {@link UserContract.View}
     */
    public UserModule(UserContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserContract.View provideUserView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserContract.Model provideUserModel(UserModel model) {
        return model;
    }

}
