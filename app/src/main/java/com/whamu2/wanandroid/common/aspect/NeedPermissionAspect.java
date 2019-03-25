package com.whamu2.wanandroid.common.aspect;

import android.annotation.SuppressLint;

import com.android.lib.aspect.NeedPermission;
import com.blankj.utilcode.util.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author Suming
 * @date 2019/3/5
 * @address https://github.com/whamu2
 */
@Aspect
public class NeedPermissionAspect {

    @Pointcut(value = "execution(@com.android.lib.aspect.NeedPermission * *(..))")
    public void methodAnnotated() {

    }

    @SuppressLint("WrongConstant")
    @Around(value = "methodAnnotated()")
    public void aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        NeedPermission annotation = method.getAnnotation(NeedPermission.class);
        String[] permissions = annotation.permissions();
        if (permissions.length > 0) {
            PermissionUtils.permission(permissions)
                    .callback(new PermissionUtils.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            try {
                                joinPoint.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onDenied() {

                        }
                    })
                    .request();
        }
    }
}