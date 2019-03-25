package com.whamu2.wanandroid.mvp.model.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.whamu2.wanandroid.utils.database.ListConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;

/**
 * @author whamu2
 * @date 2018/6/23
 */

@Entity(nameInDb = "user_profile")
public class User extends BaseObservable {

    @Id
    private long id;
    private String email;
    private String icon;
    private String password;
    private int type;
    private String username;
    @Convert(columnType = String.class, converter = ListConverter.class)
    private List<String> collectIds;

    @Generated(hash = 586692638)
    public User() {
    }

    @Generated(hash = 580305224)
    public User(long id, String email, String icon, String password, int type,
            String username, List<String> collectIds) {
        this.id = id;
        this.email = email;
        this.icon = icon;
        this.password = password;
        this.type = type;
        this.username = username;
        this.collectIds = collectIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    public List<String> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<String> collectIds) {
        this.collectIds = collectIds;
    }
}
