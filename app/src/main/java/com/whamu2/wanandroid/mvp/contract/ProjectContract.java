package com.whamu2.wanandroid.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Cycle;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectContract {

    interface View extends IView {
        Context getContext();

        void onProjectDone(List<Cycle> treeBeans);
    }

    interface Model extends IModel {

        Observable<BaseResp<List<Cycle>>> getProjectList();

    }
}
