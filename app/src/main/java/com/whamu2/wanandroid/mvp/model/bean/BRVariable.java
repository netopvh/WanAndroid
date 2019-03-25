package com.whamu2.wanandroid.mvp.model.bean;

import android.support.annotation.Nullable;

/**
 * @author Suming
 * @date 2019/3/7
 * @address https://github.com/whamu2
 */
public class BRVariable {
    private int variableId;
    @Nullable
    private Object value;

    public BRVariable(int variableId, @Nullable Object value) {
        this.variableId = variableId;
        this.value = value;
    }

    public int getVariableId() {
        return variableId;
    }

    public void setVariableId(int variableId) {
        this.variableId = variableId;
    }

    @Nullable
    public Object getValue() {
        return value;
    }

    public void setValue(@Nullable Object value) {
        this.value = value;
    }
}
