package com.whamu2.wanandroid.mvp.model.bean

import android.databinding.Bindable
import com.google.gson.annotations.SerializedName

/**
 * kotlin 数据类创建方式
 * @author Suming
 * @date 2019/3/14
 * @address https://github.com/whamu2
 */

data class Page<T>(
        @SerializedName("curPage") var curPage: Int = 0,
        @SerializedName("offset") var offset: Int = 0,
        @SerializedName("pageCount") var pageCount: Int = 0,
        @SerializedName("size") var size: Int = 0,
        @SerializedName("total") var total: Int = 0,
        @SerializedName("datas") var datas: List<T>
)

data class Collect(
        @SerializedName("author") var author: String? = null,
        @SerializedName("chapterId") var chapterId: Int = 0,
        @SerializedName("chapterName") var chapterName: String? = null,
        @SerializedName("courseId") var courseId: Int = 0,
        @SerializedName("desc") var desc: String? = null,
        @SerializedName("envelopePic") var envelopePic: String? = null,
        @SerializedName("id") var id: Int = 0,
        @SerializedName("link") var link: String? = null,
        @SerializedName("niceDate") var niceDate: String? = null,
        @SerializedName("origin") var origin: String? = null,
        @SerializedName("originId") var originId: Int = 0,
        @SerializedName("publishTime") var publishTime: Long = 0,
        @Bindable @SerializedName("title") var title: String? = null,
        @SerializedName("userId") var userId: Int = 0,
        @SerializedName("visible") var visible: Int = 0,
        @SerializedName("zan") var zan: Int = 0
)
