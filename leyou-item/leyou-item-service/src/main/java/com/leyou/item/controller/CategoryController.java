package com.leyou.item.controller;


import com.leyou.item.pojo.Category;
import com.leyou.item.service.ICateGoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XuHao
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private ICateGoryService cateGoryService;

    /**
     * 根据cid3查询所有的cid1、cid2、cid3对应的category对象
     * @param cid3
     * @return
     */
    @GetMapping("all/level")
    public ResponseEntity<List<Category>> queryAllCategoryByCid3(@RequestParam("cid3") Long cid3){

        List<Category> categoryList = cateGoryService.queryAllCategoryByCid3(cid3);

        if(CollectionUtils.isEmpty(categoryList)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryList);
    }


    /**
     * 根据父id查询所有商品分类
     * @param pid
     * @return
     */
    @RequestMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByParentId(@RequestParam("pid")Long pid){

        try {
            if (pid == null || pid.longValue() < 0){
                return ResponseEntity.badRequest().build();
            }

            List<Category> categoryList = this.cateGoryService.queryCategoryListByParentId(pid);

            if(CollectionUtils.isEmpty(categoryList)){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 根据品牌bid查询商品分类
     * @param bid 品牌id
     * @return 商品分类集合List<Category>
     */
    @RequestMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryListByBid(@PathVariable("bid")Long bid){

        try {
            if (bid == null || bid.longValue() < 0){
                return ResponseEntity.badRequest().build();
            }

            List<Category> categoryList = this.cateGoryService.queryCategoryListByBid(bid);
            if(CollectionUtils.isEmpty(categoryList)){
                return ResponseEntity.notFound().build();
            }

            System.out.println(categoryList);
            return ResponseEntity.ok(categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 根据ids批量查询分类信息
     * @param ids
     * @return
     */
    @GetMapping
    public ResponseEntity<List<String>> queryCategoryNamesByCids(@RequestParam("ids") List<Long> ids){

        List<Category> categoryList = cateGoryService.queryCategoryListByCids(ids);

        List<String> nameList = categoryList.stream().map(category -> category.getName()).collect(Collectors.toList());


        if(!CollectionUtils.isEmpty(categoryList)){
            return ResponseEntity.ok(nameList);
        }
        return ResponseEntity.notFound().build();
    }

}
