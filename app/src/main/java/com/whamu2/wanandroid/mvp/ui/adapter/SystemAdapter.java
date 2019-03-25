package com.whamu2.wanandroid.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseBindingViewHolder;
import com.whamu2.wanandroid.databinding.ItemSystemBinding;
import com.whamu2.wanandroid.mvp.model.bean.Cycle;

/**
 * @author Suming
 * @date 2019/3/12
 * @address https://github.com/whamu2
 */
public class SystemAdapter extends BaseQuickAdapter<Cycle, SystemAdapter.ViewHolder> {
    public static SystemAdapter create() {
        return new SystemAdapter(R.layout.item_system);
    }

    private SystemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, Cycle item) {
        helper.mViewDataBinding.setItem(item);
    }

    public class ViewHolder extends BaseBindingViewHolder<ItemSystemBinding> {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
