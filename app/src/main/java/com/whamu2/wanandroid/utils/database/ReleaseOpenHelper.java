package com.whamu2.wanandroid.utils.database;

import android.content.Context;

import com.whamu2.wanandroid.mvp.model.bean.DaoMaster;

/**
 * @author whamu2
 * @date 2018/8/28
 */
public class ReleaseOpenHelper extends DaoMaster.OpenHelper {

    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }
}
