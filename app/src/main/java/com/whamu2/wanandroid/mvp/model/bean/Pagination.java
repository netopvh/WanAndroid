package com.whamu2.wanandroid.mvp.model.bean;

import java.util.List;

/**
 * 分页
 *
 * @author whamu2
 * @date 2018/6/26
 */
public class Pagination {
    /**
     * curPage : 0
     * datas :
     * offset : -20
     * over : false
     * pageCount : 34
     * size : 20
     * total : 680
     */
    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<Articles> datas;

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

    public List<Articles> getDatas() {
        return datas;
    }

    public void setDatas(List<Articles> datas) {
        this.datas = datas;
    }
}
