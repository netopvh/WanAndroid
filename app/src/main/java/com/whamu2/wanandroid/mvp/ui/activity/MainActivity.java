package com.whamu2.wanandroid.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseDataBindingActivity;
import com.whamu2.wanandroid.databinding.ActivityMainBinding;
import com.whamu2.wanandroid.mvp.ui.fragment.MineFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.ProjectFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.SystemFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.TopArticleFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.WechatFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Suming
 * @date 2019/3/7
 * @address https://github.com/whamu2
 */
public class MainActivity extends BaseDataBindingActivity<ActivityMainBinding> {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(TopArticleFragment.newInstance());
        adapter.addFragment(SystemFragment.newInstance());
        adapter.addFragment(ProjectFragment.newInstance());
        adapter.addFragment(WechatFragment.newInstance());
        adapter.addFragment(MineFragment.newInstance());
        mViewBinding.viewpager.setOffscreenPageLimit(4);
        mViewBinding.viewpager.setAdapter(adapter);

        mViewBinding.navigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                mViewBinding.viewpager.setCurrentItem(0);
                break;
            case R.id.navigation_system:
                mViewBinding.viewpager.setCurrentItem(1);
                break;
            case R.id.navigation_project:
                mViewBinding.viewpager.setCurrentItem(2);
                break;
            case R.id.navigation_publicnumber:
                mViewBinding.viewpager.setCurrentItem(3);
                break;
            case R.id.navigation_notifications:
                mViewBinding.viewpager.setCurrentItem(4);
                break;
        }
        return true;
    }

    class Adapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();

        Adapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
