package com.whamu2.wanandroid.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseBindingViewHolder;
import com.whamu2.wanandroid.databinding.ItemProjectBinding;
import com.whamu2.wanandroid.mvp.model.bean.Articles;

/**
 * @author Suming
 * @date 2019/3/12
 * @address https://github.com/whamu2
 */
public class ProjectAdapter extends BaseQuickAdapter<Articles, ProjectAdapter.ViewHolder> {

    public static ProjectAdapter create() {
        return new ProjectAdapter(R.layout.item_project);
    }

    private ProjectAdapter(int layoutResId) {
        super(layoutResId);
        openLoadAnimation();
    }

    @Override
    protected void convert(ViewHolder helper, Articles item) {
        helper.mViewDataBinding.setItem(item);
    }

    public class ViewHolder extends BaseBindingViewHolder<ItemProjectBinding> {

        public ViewHolder(View view) {
            super(view);
        }
    }
}
