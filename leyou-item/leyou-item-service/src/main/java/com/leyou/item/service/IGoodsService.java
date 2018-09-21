package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * @author XuHao
 * @Title: IGoodsService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/621:40
 */
public interface IGoodsService {
    /**
     * 查詢商品
     * @param key 查詢條件：模糊查詢關鍵字
     * @param saleable  查詢條件：是否上架
     * @param page 分頁條件： 當前頁碼
     * @param rows 分頁條件：每頁記錄數
     * @return
     */
    PageResult<SpuBo> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key);

    /**
     * 添加商品信息
     * @param spuBo
     * @return
     */
    Boolean addGoods(SpuBo spuBo);

    /**
     * 根據id查詢商品
     * @param id
     * @return
     */
    Spu querySpuById(Long id);

    /**
     * 根據spuid查詢sku列表
     * @param id
     * @return
     */
    List<Sku> querySkuListBySpuId(Long id);

    /**
     * 根據spuid查詢spudetail
     * @param id
     * @return
     */
    SpuDetail querySpuDetailById(Long id);

    /**
     * 更新商品
     * @param spuBo
     * @return
     */
    Boolean updateGoods(SpuBo spuBo);

    /**
     * 刪除商品
     * @param id
     * @return
     */
    Boolean deleteSpu(long id);

    Sku querySkuBySkuId(Long skuId);
}
