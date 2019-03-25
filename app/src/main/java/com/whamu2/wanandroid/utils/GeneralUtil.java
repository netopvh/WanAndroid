package com.whamu2.wanandroid.utils;

import android.support.annotation.IntRange;

import com.whamu2.wanandroid.common.Container;

/**
 * @author Suming
 * @date 2019/3/13
 * @address https://github.com/whamu2
 */
public class GeneralUtil {

    /**
     * 判断是否是首页
     *
     * @param page 页码
     */
    public static boolean isFirstPage(@IntRange(from = 1) int page) {
        return page == Container.Const.DEFAULT_PAGE;
    }
}
