package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.IGoodsService;
import com.leyou.item.bo.SpuBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XuHao
 * @Title: GoodsController
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/621:27
 */
@Controller
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;


    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id")Long id){
        Spu spu = goodsService.querySpuById(id);
        if(spu == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(spu);


    }

    /**
     * 查詢商品
     * @param key 查詢條件：模糊查詢關鍵字
     * @param saleable  查詢條件：是否上架
     * @param page 分頁條件： 當前頁碼
     * @param rows 分頁條件：每頁記錄數
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(@RequestParam(value = "key",required = false)String key,
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

        Boolean flag = goodsService.addGoods(spuBo);
        if(flag){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 根据spu_id查询spudeail
     * @param id
     * @return
     */
    @GetMapping("spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable("id")Long id){

        SpuDetail spuDetail = goodsService.querySpuDetailById(id);

        if(spuDetail != null){
            return ResponseEntity.ok(spuDetail);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuListBySpuId(@RequestParam("id")Long id){
        List<Sku> skuList = goodsService.querySkuListBySpuId(id);

        if(!CollectionUtils.isEmpty(skuList)){
            return ResponseEntity.ok(skuList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        Boolean flag = goodsService.updateGoods(spuBo);
        if(flag){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("spu")
    public ResponseEntity<Void> deleteSpu(@RequestParam("id")long id){

        Boolean flag = this.goodsService.deleteSpu(id);

        if(flag){

            //返回删除成功状态码
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

}
