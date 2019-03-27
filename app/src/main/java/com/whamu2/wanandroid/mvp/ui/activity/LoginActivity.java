package com.whamu2.wanandroid.mvp.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.base.BaseLifecycleDataBindingActivity;
import com.whamu2.wanandroid.common.event.EventObj;
import com.whamu2.wanandroid.databinding.ActivityLoginBinding;
import com.whamu2.wanandroid.di.component.DaggerUserComponent;
import com.whamu2.wanandroid.di.model.UserModule;
import com.whamu2.wanandroid.mvp.contract.UserContract;
import com.whamu2.wanandroid.mvp.model.bean.User;
import com.whamu2.wanandroid.mvp.presenter.UserPresenter;
import com.whamu2.wanandroid.utils.database.DatabaseManager;

import org.simple.eventbus.EventBus;

import static com.whamu2.wanandroid.common.Container.Event.LOGIN;

/**
 * @author whamu2
 * @date 2018/6/23
 */
public class LoginActivity extends BaseLifecycleDataBindingActivity<ActivityLoginBinding, UserPresenter> implements UserContract.View {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int STATE_LOGIN = 166;
    private static final int STATE_REGISTER = 68;
    private int currentState;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {

        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setupToolbar(mViewBinding.toolbar, "登录注册");
        switchState(STATE_LOGIN);
        mViewBinding.password.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });
        mViewBinding.setListener(this::onClick);
    }

    void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_or_register_button:
                attemptLogin();
                break;
            case R.id.goRegister:
                switch (currentState) {
                    case STATE_LOGIN:
                        switchState(STATE_REGISTER);
                        break;
                    case STATE_REGISTER:
                        switchState(STATE_LOGIN);
                        break;
                }
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onLoginComplete(User user) {
        KeyboardUtils.hideSoftInput(this);
        DatabaseManager.getInstance().saveUser(user);
        showMessage(getString(R.string.str_sign_complete));
        EventBus.getDefault().post(new EventObj(LOGIN));
        killMyself();
    }

    @Override
    public void onRegisterComplete(User user) {
        KeyboardUtils.hideSoftInput(this);
        showMessage(getString(R.string.str_sign_complete));
        killMyself();
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showProgress(false);
    }

    private void attemptLogin() {
        mViewBinding.username.setError(null);
        mViewBinding.password.setError(null);
        mViewBinding.againPassword.setError(null);

        String email = mViewBinding.username.getText().toString();
        String password = mViewBinding.password.getText().toString();
        String againPassword = mViewBinding.againPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mViewBinding.password.setError(getString(R.string.error_invalid_password));
            focusView = mViewBinding.password;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mViewBinding.username.setError(getString(R.string.error_field_required));
            focusView = mViewBinding.username;
            cancel = true;
        }

        if (currentState == STATE_LOGIN) {
            if (cancel) {
                focusView.requestFocus();
            } else {
                showProgress(true);
                // login
                if (mPresenter != null) {
                    mPresenter.goLogin(email, password);
                }
            }
        } else if (currentState == STATE_REGISTER) {
            if (!TextUtils.equals(password, againPassword)) {
                mViewBinding.againPassword.setError(getString(R.string.error_invalid_password_same));
                focusView = mViewBinding.againPassword;
                cancel = true;
            }

            if (cancel) {
                focusView.requestFocus();
            } else {
                showProgress(true);
                // register
                if (mPresenter != null) {
                    mPresenter.register(email, password, againPassword);
                }
            }
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mViewBinding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        mViewBinding.loginForm.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mViewBinding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mViewBinding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        mViewBinding.loginProgress.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mViewBinding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showLong(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {
        finish();
    }

    private void switchState(int state) {
        if (state == STATE_LOGIN) {
            mViewBinding.username.setText(null);
            mViewBinding.password.setText(null);
            mViewBinding.againPassword.setVisibility(View.GONE);
            mViewBinding.goRegister.setText(R.string.str_hint_register);
            mViewBinding.signInOrRegisterButton.setText(R.string.action_sign_in);
            currentState = STATE_LOGIN;
        } else {
            mViewBinding.username.setText(null);
            mViewBinding.password.setText(null);
            mViewBinding.againPassword.setVisibility(View.VISIBLE);
            mViewBinding.goRegister.setText(R.string.str_hint_login);
            mViewBinding.signInOrRegisterButton.setText(R.string.action_register);
            currentState = STATE_REGISTER;
        }

    }
}
