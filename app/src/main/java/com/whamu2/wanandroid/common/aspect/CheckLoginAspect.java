package com.whamu2.wanandroid.common.aspect;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.blankj.utilcode.util.ActivityUtils;
import com.whamu2.wanandroid.App;
import com.whamu2.wanandroid.mvp.model.bean.User;
import com.whamu2.wanandroid.mvp.ui.activity.LoginActivity;
import com.whamu2.wanandroid.utils.database.DatabaseManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Suming
 * @date 2019/3/6
 * @address https://github.com/whamu2
 */
@Aspect
public class CheckLoginAspect {

    @Pointcut(value = "execution(@com.android.lib.aspect.CheckLogin * *(..))")
    public void getAnnotation() {
    }

    @Around("getAnnotation()")
    public void doMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        User user = DatabaseManager.getInstance().getUser();
        if (user == null) {
            new AlertDialog.Builder(App.getApplication().getCurActivity())
                    .setTitle("请先登录")
                    .setMessage("您还未登录请前往登录!")
                    .setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityUtils.startActivity(LoginActivity.class);
                        }
                    })
                    .create()
                    .show();
            return;
        }
        joinPoint.proceed();//执行原方法
    }
}
