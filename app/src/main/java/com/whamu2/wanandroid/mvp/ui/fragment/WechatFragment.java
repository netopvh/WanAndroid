package com.whamu2.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseLifecycleDataBindingFragment;
import com.whamu2.wanandroid.databinding.FragmentWechatBinding;
import com.whamu2.wanandroid.di.component.DaggerWechatComponent;
import com.whamu2.wanandroid.di.model.WechatModule;
import com.whamu2.wanandroid.mvp.contract.WechatCot;
import com.whamu2.wanandroid.mvp.model.bean.WechatNumber;
import com.whamu2.wanandroid.mvp.presenter.WechatPresenter;
import com.whamu2.wanandroid.mvp.ui.adapter.TabTitleAdapter;

import java.util.List;

/**
 * 公众号
 *
 * @author Suming
 * @date 2019/3/12
 * @address https://github.com/whamu2
 */
public class WechatFragment extends BaseLifecycleDataBindingFragment<FragmentWechatBinding, WechatPresenter> implements WechatCot.View {

    public static WechatFragment newInstance() {
        Bundle args = new Bundle();
        WechatFragment fragment = new WechatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWechatComponent.builder()
                .appComponent(appComponent)
                .wechatModule(new WechatModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wechat, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.getNumberList();
        }
    }

    @Override
    public void onNumberDone(List<WechatNumber> numbers) {
        TabTitleAdapter adapter = new TabTitleAdapter(getChildFragmentManager());
        for (WechatNumber number : numbers) {
            adapter.addTab(WechatChildFragment.newInstance(number.getId()), number.getName());
        }
        mViewBinding.viewpager.setAdapter(adapter);
        mViewBinding.tlBar.setupWithViewPager(mViewBinding.viewpager);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showLong(message);

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
