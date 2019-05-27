package com.whamu2.wanandroid.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseLifecycleDataBindingActivity;
import com.whamu2.wanandroid.databinding.ActivityCollectBinding;
import com.whamu2.wanandroid.mvp.contract.FavoritesCot;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.presenter.FavoritesPresenter;

import java.util.List;

/**
 * @author suming
 * @date 15:39 2019 May Mon
 * @des https://github.com/whamu2
 */
public class FavoritesActivity extends BaseLifecycleDataBindingActivity<ActivityCollectBinding, FavoritesPresenter> implements FavoritesCot.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_collect;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDataDone(List<Articles> data, int curPage) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
