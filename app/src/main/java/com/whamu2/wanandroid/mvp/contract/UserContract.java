package com.whamu2.wanandroid.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.User;

import io.reactivex.Observable;


public interface UserContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        void onLoginComplete(User user);

        void onRegisterComplete(User user);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {
        Observable<BaseResp<User>> register(String username, String password, String repassword);

        Observable<BaseResp<User>> login(String username, String password);
    }
}
