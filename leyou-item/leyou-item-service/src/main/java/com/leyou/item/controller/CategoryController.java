package com.leyou.item.controller;


import com.leyou.item.pojo.Category;
import com.leyou.item.service.ICateGoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuHao
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private ICateGoryService cateGoryService;

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

}
