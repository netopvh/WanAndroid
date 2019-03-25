package com.whamu2.wanandroid.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.whamu2.wanandroid.utils.Joiner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author whamu2
 * @date 2018/6/26
 */
public class Cycle implements Parcelable {

    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;
    private ArrayList<Cycle> children;


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public ArrayList<Cycle> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Cycle> children) {
        this.children = children;
    }

    public String getChildrenNames() {
        List<String> names = new ArrayList<>();
        for (Cycle bean : getChildren()) {
            names.add(bean.name);
        }
        return Joiner.on(", ").join(names);
    }

    protected Cycle(Parcel in) {
        courseId = in.readInt();
        id = in.readInt();
        name = in.readString();
        order = in.readInt();
        parentChapterId = in.readInt();
        visible = in.readInt();
        children = in.createTypedArrayList(Cycle.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(courseId);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeInt(parentChapterId);
        dest.writeInt(visible);
        dest.writeTypedList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cycle> CREATOR = new Creator<Cycle>() {
        @Override
        public Cycle createFromParcel(Parcel in) {
            return new Cycle(in);
        }

        @Override
        public Cycle[] newArray(int size) {
            return new Cycle[size];
        }
    };

}
