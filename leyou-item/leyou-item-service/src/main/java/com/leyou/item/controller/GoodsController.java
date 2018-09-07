package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.service.IGoodsService;
import com.leyou.item.pojo.SpuBo;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author XuHao
 * @Title: GoodsController
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/621:27
 */
@RestController
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;


    /**
     * 查詢商品
     * @param key 查詢條件：模糊查詢關鍵字
     * @param saleable  查詢條件：是否上架
     * @param page 分頁條件： 當前頁碼
     * @param rows 分頁條件：每頁記錄數
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> queryBrandByPage(@RequestParam(value = "key",required = false)String key,
                                                              @RequestParam(value = "saleable",required = false)Boolean saleable,
                                                              @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                              @RequestParam(value = "rows",defaultValue = "5") Integer rows){

        PageResult<SpuBo> pageResult = goodsService.querySpuByPage(page,rows,saleable,key);
        if(!CollectionUtils.isEmpty(pageResult.getItems())){

            return ResponseEntity.ok(pageResult);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("goods")
    public ResponseEntity<Void> addGoods(@RequestBody SpuBo spuBo){

        System.out.println(spuBo);

        Boolean flag = goodsService.addGoods(spuBo);
        if(flag){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
