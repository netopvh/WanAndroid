package com.android.lib.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 底部菜单
 *
 * @author Eric
 * @date 2019/4/4
 * @github https://github.com/whamu2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Tab {
    /**
     * tab标题
     */
    String tabName() default "";

    /**
     * 图标资源
     */
    int srcource() default 0;
}
