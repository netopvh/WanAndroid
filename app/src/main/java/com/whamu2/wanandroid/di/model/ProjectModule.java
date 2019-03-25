package com.whamu2.wanandroid.di.model;

import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.mvp.contract.ProjectContract;
import com.whamu2.wanandroid.mvp.model.ProjectModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@Module
public class ProjectModule {
    private ProjectContract.View view;

    public ProjectModule(ProjectContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProjectContract.View provideTreeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ProjectContract.Model provideTreeModel(ProjectModel model) {
        return model;
    }

}
