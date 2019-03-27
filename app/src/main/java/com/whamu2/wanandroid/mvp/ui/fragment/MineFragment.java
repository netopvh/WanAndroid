package com.whamu2.wanandroid.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lib.aspect.CheckLogin;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseLifecycleDataBindingFragment;
import com.whamu2.wanandroid.common.event.EventObj;
import com.whamu2.wanandroid.databinding.FragmentMineBinding;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.User;
import com.whamu2.wanandroid.utils.SingletonLoadHelper;
import com.whamu2.wanandroid.utils.Transformer;
import com.whamu2.wanandroid.utils.database.DatabaseManager;
import com.whamu2.wanandroid.utils.observer.Callback;
import com.whamu2.wanandroid.utils.observer.CommonSubscriber;

import java.util.Objects;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.whamu2.wanandroid.common.Container.Event.LOGIN;

/**
 * @author Eric
 * @date 2019/3/27
 * @github https://github.com/whamu2
 */
public class MineFragment extends BaseLifecycleDataBindingFragment<FragmentMineBinding, IPresenter> {
    private static final String TAG = "MineFragment";

    private RxErrorHandler mErrorHandler;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mErrorHandler = ArmsUtils.obtainAppComponentFromContext(Objects.requireNonNull(getContext())).rxErrorHandler();
        setupOwner();
        mViewBinding.setListener(this::onClick);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                SingletonLoadHelper.register(getContext())
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
                break;
            default:
                onCheckLogin(view.getId());
                break;
        }
    }

    @Override
    protected void onEventSubscribe(EventObj obj) {
        super.onEventSubscribe(obj);
        if (obj.getKey() == LOGIN) {
            setupOwner();
        }
    }

    @CheckLogin
    public void onCheckLogin(int id) {
        setupOwner();
        switch (id) {
            case R.id.head:
                break;
            case R.id.collect:
                break;
        }
    }

    private void setupOwner() {
        User user = DatabaseManager.getInstance().getUser();
        mViewBinding.logout.setVisibility(user != null ? View.VISIBLE : View.GONE);
        mViewBinding.setLocaluser(user);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
    }
}
