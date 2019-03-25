package com.whamu2.wanandroid.mvp.contract;

import android.support.v4.app.Fragment;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Cycle;

import java.util.List;

import io.reactivex.Observable;

/**
 * 知识体系
 *
 * @author whamu2
 * @date 2018/6/26
 */
public interface TreeContract {

    interface View extends IView {
        Fragment getFragment();

        void loadTreeSuc(List<Cycle> treeBeans);
    }

    interface Model extends IModel {

        Observable<BaseResp<List<Cycle>>> getTree(boolean update);

    }
}
