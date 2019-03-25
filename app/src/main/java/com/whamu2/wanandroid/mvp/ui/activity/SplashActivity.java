package com.whamu2.wanandroid.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.android.lib.aspect.NeedPermission;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseDataBindingActivity;
import com.whamu2.wanandroid.databinding.ActivitySplashBinding;

/**
 * @author Suming
 * @date 2019/3/7
 * @address https://github.com/whamu2
 */
public class SplashActivity extends BaseDataBindingActivity<ActivitySplashBinding> {
    private AlphaAnimation mAlphaAnimation;

    @Override
    public int initView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mAlphaAnimation = new AlphaAnimation(0.3F, 1.0F);
        mAlphaAnimation.setDuration(2000);
        mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                requestPermission();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mViewBinding.layoutSplash.setAnimation(mAlphaAnimation);
    }

    @NeedPermission(permissions = {PermissionConstants.STORAGE, PermissionConstants.PHONE})
    public void requestPermission() {
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAlphaAnimation.reset();
    }
}
