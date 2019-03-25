package com.whamu2.wanandroid.mvp.model.bean;

/**
 * @author whamu2
 * @date 2018/6/23
 */
public class BaseResp<D> {

    /**
     * data : ...
     * errorCode : 0
     * errorMsg :
     */

    private D data;
    private int errorCode;
    private String errorMsg;

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return errorCode == 0;
    }
}
