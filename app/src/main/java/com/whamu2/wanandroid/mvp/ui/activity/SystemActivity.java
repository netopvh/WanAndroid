package com.whamu2.wanandroid.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseDataBindingActivity;
import com.whamu2.wanandroid.databinding.ActivitySystemBinding;
import com.whamu2.wanandroid.mvp.model.bean.Cycle;
import com.whamu2.wanandroid.mvp.ui.adapter.TabAdapter;
import com.whamu2.wanandroid.mvp.ui.fragment.SubclassFragment;

/**
 * @author Suming
 * @date 2019/3/12
 * @address https://github.com/whamu2
 */
public class SystemActivity extends BaseDataBindingActivity<ActivitySystemBinding> {
    private static final String TAG = SystemActivity.class.getSimpleName();
    private static final String KEY_DATA = "DATA";

    public static void start(Context context, Cycle bean) {
        Intent starter = new Intent(context, SystemActivity.class);
        starter.putExtra(KEY_DATA, bean);
        context.startActivity(starter);
    }

    @Override
    public int initView() {
        return R.layout.activity_system;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Cycle bean = getIntent().getParcelableExtra(KEY_DATA);
        setupToolbar(mViewBinding.toolbar, bean.getName());

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());

        for (Cycle treeBean : bean.getChildren()) {
            adapter.addTab(SubclassFragment.newInstance(treeBean.getId()), treeBean.getName());
        }

        mViewBinding.viewpager.setAdapter(adapter);
        mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewpager);
    }
}
