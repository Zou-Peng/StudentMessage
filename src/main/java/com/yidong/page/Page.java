package com.yidong.page;

import org.springframework.stereotype.Component;

@Component
public class Page {
    //当前页面
    private int page;
    //每页显示数
    private int rows;
    //每页头一条信息的条目数字
    private int offset;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        this.offset=(page-1)*rows;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset=(page-1)*rows;
    }
}
