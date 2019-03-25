package com.whamu2.wanandroid.mvp.model.bean;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author whamu2
 * @date 2018/6/23
 */

public class LocalUser {

    private String username;
    private String headImageUrl;
    private String description;

    public LocalUser(String username, String headImageUrl, String description) {
        this.username = username;
        this.headImageUrl = headImageUrl;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @BindingAdapter({"headImageUrl"})
    public static void loadImage(ImageView imageView, String imgUrl) {
        //必须为static方法，否则报错
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .into(imageView);
    }
}
