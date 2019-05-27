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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.android.lib.aspect.CheckLogin;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.utils.ArmsUtils;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseDataBindingActivity;
import com.whamu2.wanandroid.common.event.EventObj;
import com.whamu2.wanandroid.databinding.ActivityMainBinding;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.User;
import com.whamu2.wanandroid.mvp.ui.adapter.TabAdapter;
import com.whamu2.wanandroid.mvp.ui.fragment.MineFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.ProjectFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.SystemFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.TopArticleFragment;
import com.whamu2.wanandroid.mvp.ui.fragment.WechatFragment;
import com.whamu2.wanandroid.utils.SingletonLoadHelper;
import com.whamu2.wanandroid.utils.Transformer;
import com.whamu2.wanandroid.utils.database.DatabaseManager;
import com.whamu2.wanandroid.utils.observer.Callback;
import com.whamu2.wanandroid.utils.observer.CommonSubscriber;

import org.jetbrains.annotations.NotNull;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.whamu2.wanandroid.common.Container.Event.LOGIN;

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
    private NavigationView mNavigationView;
    private View mHeaderView;
    private BottomNavigationView mBottomNavigationView;
    private TextView nameTextView;

    private RxErrorHandler mErrorHandler;

    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.viewpager);
        mBottomNavigationView = findViewById(R.id.navigation);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        mErrorHandler = ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler();

        /**
         * 侧栏导航设置
         */
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        mHeaderView = mNavigationView.getHeaderView(0);
        handlerHeaderView();

        handlerBottomNavigation();
    }

    /**
     * 底部导航设置
     */
    private void handlerBottomNavigation() {
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

    /**
     * 头部设置
     */
    private void handlerHeaderView() {
        ImageView headerImageView = mHeaderView.findViewById(R.id.headerImageView);
        nameTextView = mHeaderView.findViewById(R.id.nameTextView);
        User user = DatabaseManager.getInstance().getUser();
        if (user == null) {
            headerImageView.setOnClickListener(v -> onCheckLogin(R.id.imageView));
        } else {
            nameTextView.setText(user.getUsername());
        }
    }

    @CheckLogin
    public void onCheckLogin(int id) {
        setupOwner();
        if (id == R.id.nav_favorites) {
            ToastUtils.showShort("收藏");
        }
    }

    private void setupOwner() {
        User user = DatabaseManager.getInstance().getUser();
        if (user != null) {
            nameTextView.setText(user.getUsername());
        } else {
            nameTextView.setText(R.string.app_name);
        }
    }

    @Override
    protected void onEventSubscribe(EventObj obj) {
        super.onEventSubscribe(obj);
        if (obj.getKey() == LOGIN) {
            setupOwner();
        }
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
            // 收藏
            onCheckLogin(R.id.nav_favorites);
        } else if (id == R.id.nav_theme_mode) {
            // 夜晚模式
        } else if (id == R.id.nav_settings) {
            // 设置
        } else if (id == R.id.nav_share) {
            // 分享
        } else if (id == R.id.nav_logout) {
            // 登出
            User user = DatabaseManager.getInstance().getUser();
            if (user != null) {
                SingletonLoadHelper.register(this)
                        .logout()
                        .compose(Transformer.cutoverSchedulers())
                        .subscribe(new CommonSubscriber<>(mErrorHandler, new Callback<BaseResp<Object>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BaseResp<Object> resp) {
                                if (resp.isSuccess()) {
                                    DatabaseManager.getInstance().clear();
                                    ToastUtils.showLong(getString(R.string.str_logout_done));
                                    setupOwner();
                                } else {
                                    ToastUtils.showLong(resp.getErrorMsg());
                                }

                            }
                        }));
            } else {
                ToastUtils.showShort("你还未登录");
            }
        } else {
            ToastUtils.showShort("敬请期待");
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
