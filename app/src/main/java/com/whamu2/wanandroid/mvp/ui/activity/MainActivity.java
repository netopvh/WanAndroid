package com.whamu2.wanandroid.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseDataBindingActivity;
import com.whamu2.wanandroid.databinding.ActivityMainBinding;
import com.whamu2.wanandroid.mvp.ui.adapter.TabAdapter;
import com.whamu2.wanandroid.mvp.ui.fragment.MineFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.ProjectFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.SystemFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.TopArticleFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.WechatFragment;

import org.jetbrains.annotations.NotNull;

/**
 * @author Suming
 * @date 2019/3/7
 * @address https://github.com/whamu2
 */
public class MainActivity extends BaseDataBindingActivity<ActivityMainBinding> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;

    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.viewpager);
        BottomNavigationView mBottomNavigationView = findViewById(R.id.navigation);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView mNavigationView = findViewById(R.id.nav_view);

        /**
         * 侧栏导航设置
         */
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        /**
         * 底部导航设置
         */
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(TopArticleFragment.newInstance());
        adapter.addFragment(SystemFragment.newInstance());
        adapter.addFragment(ProjectFragment.newInstance());
        adapter.addFragment(WechatFragment.newInstance());
        adapter.addFragment(MineFragment.newInstance());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(adapter);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    mToolbar.setTitle(R.string.title_home);
                    break;
                case R.id.navigation_system:
                    mViewPager.setCurrentItem(1);
                    mToolbar.setTitle(R.string.title_system);
                    break;
                case R.id.navigation_project:
                    mViewPager.setCurrentItem(2);
                    mToolbar.setTitle(R.string.title_project);
                    break;
                case R.id.navigation_publicnumber:
                    mViewPager.setCurrentItem(3);
                    mToolbar.setTitle(R.string.title_publicnumber);
                    break;
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_favorites) {
            // Handle the camera action
        } else if (id == R.id.nav_theme_mode) {


        } else if (id == R.id.nav_share) {

        } else {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
