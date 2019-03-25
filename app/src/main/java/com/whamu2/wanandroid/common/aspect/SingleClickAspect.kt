package com.eric.android.glidemodule.aop.aspect

import com.android.lib.aspect.SingleClick
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import java.util.*

/**
 * @author Suming
 * @date 2019/3/1
 * @address https://github.com/whamu2
 */
@Aspect
class SingleClickAspect {

    companion object {
        var lastClickTime = 0L
    }

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.android.lib.aspect.SingleClick * *(..))")
    fun methodAnnotated() {

    }

    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundMethod(joinPoint: ProceedingJoinPoint) {
        val signature = joinPoint.signature as MethodSignature
        val annotation = signature.method.getAnnotation(SingleClick::class.java)
        val intervalMillis = annotation.intervalMillis
        val timeInMillis = Calendar.getInstance().timeInMillis
        if (timeInMillis - lastClickTime > intervalMillis) {
            lastClickTime = timeInMillis
            joinPoint.proceed()
        }
    }
}

