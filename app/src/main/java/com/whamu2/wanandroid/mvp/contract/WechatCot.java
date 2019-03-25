package com.whamu2.wanandroid.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.WechatNumber;

import java.util.List;

import io.reactivex.Observable;

public interface WechatCot {

    interface View extends IView {
        Context getContext();

        void onNumberDone(List<WechatNumber> numbers);
    }

    interface Model extends IModel {

        Observable<BaseResp<List<WechatNumber>>> getNumberList();

    }
}
