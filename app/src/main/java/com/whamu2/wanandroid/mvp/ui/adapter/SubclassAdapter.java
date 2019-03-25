package com.whamu2.wanandroid.mvp.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.whamu2.wanandroid.App;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.databinding.ItemSubclassLayoutBinding;
import com.whamu2.wanandroid.mvp.model.bean.Articles;

/**
 * @author whamu2
 * @date 2018/7/3
 */
public class SubclassAdapter extends BaseQuickAdapter<Articles, SubclassAdapter.ViewHolder> {

    public static SubclassAdapter create() {
        return new SubclassAdapter(R.layout.item_subclass_layout);
    }

    private SubclassAdapter(int layoutResId) {
        super(layoutResId);
        openLoadAnimation();
    }

    @Override
    protected void convert(ViewHolder helper, Articles item) {
        helper.mDataBinding.setItem(item);

        if (item.getType() == 1) {
            int color = ContextCompat.getColor(App.getApplication(), R.color.colorAccent);
            helper.mDataBinding.stvTag.setText("顶置");
            helper.mDataBinding.stvTag.setTextColor(color);
            helper.mDataBinding.stvTag.setStrokeColor(color);
            helper.mDataBinding.stvTag.setVisibility(View.VISIBLE);
        } else {
            if (TextUtils.equals(item.getSuperChapterName(), "公众号")) {
                int color = ContextCompat.getColor(App.getApplication(), R.color.lightBlue);
                helper.mDataBinding.stvTag.setText("公众号");
                helper.mDataBinding.stvTag.setTextColor(color);
                helper.mDataBinding.stvTag.setStrokeColor(color);
                helper.mDataBinding.stvTag.setVisibility(View.VISIBLE);
            } else {
                helper.mDataBinding.stvTag.setVisibility(View.GONE);
            }
        }
    }

    public class ViewHolder extends BaseViewHolder {
        private ItemSubclassLayoutBinding mDataBinding;

        public ViewHolder(View view) {
            super(view);
            mDataBinding = DataBindingUtil.bind(view);
        }
    }
}
