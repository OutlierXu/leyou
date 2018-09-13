package com.leyou.search.controller;

import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author XuHao
 * @Title: SeachControll
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1112:04
 */
@Controller
public class SeachController {

    @Autowired
    private IGoodsService goodsService;

    @PostMapping("page")
    public ResponseEntity<SearchResult<Goods>> search(@RequestBody SearchRequest request){

        SearchResult<Goods> result = (SearchResult<Goods>) goodsService.search(request);

        if(result == null || CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(result);
    }
}
