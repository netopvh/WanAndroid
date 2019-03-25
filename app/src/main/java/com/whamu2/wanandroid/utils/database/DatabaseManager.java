package com.whamu2.wanandroid.utils.database;

import android.content.Context;

import com.whamu2.wanandroid.mvp.model.bean.DaoMaster;
import com.whamu2.wanandroid.mvp.model.bean.DaoSession;
import com.whamu2.wanandroid.mvp.model.bean.User;
import com.whamu2.wanandroid.mvp.model.bean.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * @author whamu2
 * @date 2018/8/28
 */
public class DatabaseManager {

    private UserDao mDao;

    private DatabaseManager() {
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "wandroid_client.db");
        final Database db = helper.getWritableDb();
        DaoSession mSession = new DaoMaster(db).newSession();
        mDao = mSession.getUserDao();
    }

    public User getUser() {
        return mDao.queryBuilder().build().unique();
    }

    public void saveUser(User user) {
        User unique = mDao.queryBuilder().where(UserDao.Properties.Id.eq(user.getId()))
                .build()
                .unique();
        if (unique != null) {
            mDao.update(user);
        } else {
            mDao.insert(user);
        }
    }

    public void updateUser(User user) {
        User unique = mDao.queryBuilder().where(UserDao.Properties.Id.eq(user.getId()))
                .build()
                .unique();
        if (unique != null) {
            mDao.update(user);
        }
    }

    public void clear() {
        mDao.deleteAll();
    }

}
