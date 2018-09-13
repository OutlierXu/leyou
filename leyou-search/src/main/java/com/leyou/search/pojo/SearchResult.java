package com.leyou.search.pojo;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;

import java.util.List;
import java.util.Map;

/**
 * @author XuHao
 * @Title: SearchResult
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1210:23
 */

/**
 * 扩展搜索分页结果类（当前页面的品牌名称，分类信息）
 * @author XuHao
 */
public class SearchResult<Goods> extends PageResult<Goods> {

    private List<Category> categoryList;

    private List<Brand> brandList;

    //聚合之后分类的规格参数（如手机分类的规格参数）
    private List<Map<String,Object>> specs;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> specs) {
        this.specs = specs;
    }

    public SearchResult(long total, Integer totalPage, List<Goods> items, List<Category> categoryList, List<Brand> brandList, List<Map<String, Object>> specs) {

        super(total, totalPage, items);
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.specs = specs;
    }

    public SearchResult(List<Category> categoryList, List<Brand> brandList, List<Map<String, Object>> specs) {
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.specs = specs;
    }

    public SearchResult(long total, List<Goods> items, List<Category> categoryList, List<Brand> brandList, List<Map<String, Object>> specs) {
        super(total, items);
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.specs = specs;
    }
}
