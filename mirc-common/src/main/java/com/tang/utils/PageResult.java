package com.tang.utils;

import java.util.List;

public class PageResult {

    private int page;  //当前页数

    private int total;  //总页数

    private long reords; //总记录数

    private List<?> rows; //没行显示的内容

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getReords() {
        return reords;
    }

    public void setReords(long reords) {
        this.reords = reords;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
