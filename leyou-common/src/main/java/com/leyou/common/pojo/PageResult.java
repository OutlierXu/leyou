package com.leyou.common.pojo;

import sun.util.resources.ga.LocaleNames_ga;

import java.util.List;

public class PageResult<T> {

    //总条数
    private long total;
    //总页数
    private Integer totalPage;
    //当前页数据信息
    private List<T> items;

    public PageResult(long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public PageResult() {
    }

    public PageResult(long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
