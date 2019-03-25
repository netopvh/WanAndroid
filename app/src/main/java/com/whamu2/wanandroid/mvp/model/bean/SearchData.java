package com.whamu2.wanandroid.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author whamu2
 * @date 2018/7/9
 */
public class SearchData {
    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    @SerializedName("datas")
    private List<Data> data;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data implements Parcelable{

        /**
         * apkLink :
         * author : momentslz
         * chapterId : 294
         * chapterName : 完整项目
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 3044
         * link : http://www.wanandroid.com/blog/show/2171
         * niceDate : 2018-06-24
         * origin :
         * projectLink : https://github.com/momentslz/Eyepetizer
         * publishTime : 1529850040000
         * superChapterId : 294
         * superChapterName : 开源项目主Tab
         * tags : [{"name":"项目","url":"/project/list/1?cid=294"}]
         * title :
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */
        private String apkLink;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private String projectLink;
        private long publishTime;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        private ArrayList<TagsBean> tags;

        protected Data(Parcel in) {
            apkLink = in.readString();
            author = in.readString();
            chapterId = in.readInt();
            chapterName = in.readString();
            collect = in.readByte() != 0;
            courseId = in.readInt();
            desc = in.readString();
            envelopePic = in.readString();
            fresh = in.readByte() != 0;
            id = in.readInt();
            link = in.readString();
            niceDate = in.readString();
            origin = in.readString();
            projectLink = in.readString();
            publishTime = in.readLong();
            superChapterId = in.readInt();
            superChapterName = in.readString();
            title = in.readString();
            type = in.readInt();
            userId = in.readInt();
            visible = in.readInt();
            zan = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(apkLink);
            dest.writeString(author);
            dest.writeInt(chapterId);
            dest.writeString(chapterName);
            dest.writeByte((byte) (collect ? 1 : 0));
            dest.writeInt(courseId);
            dest.writeString(desc);
            dest.writeString(envelopePic);
            dest.writeByte((byte) (fresh ? 1 : 0));
            dest.writeInt(id);
            dest.writeString(link);
            dest.writeString(niceDate);
            dest.writeString(origin);
            dest.writeString(projectLink);
            dest.writeLong(publishTime);
            dest.writeInt(superChapterId);
            dest.writeString(superChapterName);
            dest.writeString(title);
            dest.writeInt(type);
            dest.writeInt(userId);
            dest.writeInt(visible);
            dest.writeInt(zan);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public ArrayList<TagsBean> getTags() {
            return tags;
        }

        public void setTags(ArrayList<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * name : 项目
             * url : /project/list/1?cid=294
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
