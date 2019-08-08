package com.sft.simulate.pojo.query;

/**
 * @author Created by yuyidi on 2019-04-15.
 * @desc
 */
public class PageAndSortQuery {

    private int page = 1;
    private int size = 20;


    public int getPage() {
        return page = page - 1;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
