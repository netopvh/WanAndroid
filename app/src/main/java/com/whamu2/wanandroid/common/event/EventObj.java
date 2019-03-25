package com.whamu2.wanandroid.common.event;

/**
 * @author whamu2
 * @date 2018/6/13
 */
public class EventObj {
    private int key;
    private Object value;

    public EventObj(int key) {
        this.key = key;
    }

    public EventObj(int key, Object value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
