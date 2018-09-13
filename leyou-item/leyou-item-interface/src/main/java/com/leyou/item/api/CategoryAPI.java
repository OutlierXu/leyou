package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author XuHao
 * @Title: CategoryAPI
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/921:54
 */
@RequestMapping("category")
public interface CategoryAPI {

    /**
     * 根据parentId查询商品分类
     * @param pid
     * @return
     */
    @RequestMapping("list")
    public List<Category> queryCategoryListByParentId(@RequestParam("pid")Long pid);

    /**
     * 根据品牌bid查询商品分类
     * @param bid 品牌id
     * @return 商品分类集合List<Category>
     */
    @RequestMapping("bid/{bid}")
    public List<Category> queryCategoryListByBid(@PathVariable("bid")Long bid);

    /**
     * 根据cids集合查询分类集合
     * @param ids 主键字符串
     * @return
     */
    @GetMapping
    public List<String> queryCategoryNamesByCids(@RequestParam("ids") List<Long> ids);
}
