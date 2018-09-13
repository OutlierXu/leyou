package com.leyou.search.pojo;

/**
 * @author XuHao
 * @Title: SearchRequest
 * @ProjectName leyou
 * @Description: 封装搜索所需要参数
 * @date 2018/9/1111:52
 */

public class SearchRequest {

    //搜索关键字
    private  String key;

    //当前页码
    private Integer page;

    //页面大小，固定20
    private static final Integer DEFAULT_SIZE = 20;

    //默认页面数1
    private static final Integer DEFAULT_PAGE = 1;

    public Integer getPage() {
        if(this.page == null){
            return DEFAULT_PAGE;
        }
        return Math.max(page, DEFAULT_PAGE);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize(){
        return DEFAULT_SIZE;
    }
}
