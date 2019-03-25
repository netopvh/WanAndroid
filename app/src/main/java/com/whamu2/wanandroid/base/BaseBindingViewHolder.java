package com.whamu2.wanandroid.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author Suming
 * @date 2019/3/12
 * @address https://github.com/whamu2
 */
public class BaseBindingViewHolder<B extends ViewDataBinding> extends BaseViewHolder {
    public B mViewDataBinding;

    public BaseBindingViewHolder(View view) {
        super(view);
        mViewDataBinding = DataBindingUtil.bind(view);
    }
}
