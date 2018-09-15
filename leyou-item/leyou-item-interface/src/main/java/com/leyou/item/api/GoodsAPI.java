package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XuHao
 * @Title: GoodsAPI
 * @ProjectName leyou
 * @Description: goods对外接口
 * @date 2018/9/921:40
 */
public interface GoodsAPI {

    /**
     * 查詢商品
     * @param key 查詢條件：模糊查詢關鍵字
     * @param saleable  查詢條件：是否上架
     * @param page 分頁條件： 當前頁碼
     * @param rows 分頁條件：每頁記錄數
     * @return 商品分页结果
     */
    @GetMapping("spu/page")
    public PageResult<SpuBo> querySpuByPage(@RequestParam(value = "key",required = false) String key,
                                            @RequestParam(value = "saleable",required = false) Boolean saleable,
                                            @RequestParam(value = "page",defaultValue = "1") Integer page,
                                            @RequestParam(value = "rows",defaultValue = "5") Integer rows);


    /**
     * 根据spu_id查询spudeail
     * @param id
     * @return
     */
    @GetMapping("spu/detail/{id}")
    public SpuDetail querySpuDetailById(@PathVariable("id")Long id);

    /**
     * 根据spu_id查询对应的sku集合
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public List<Sku> querySkuListBySpuId(@RequestParam("id")Long id);

    /**
     * 根據id查詢商品spuid
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    public Spu querySpuById(@PathVariable("id")Long id);
}
