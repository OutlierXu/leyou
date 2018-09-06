package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.SpuBo;

/**
 * @author XuHao
 * @Title: IGoodsService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/621:40
 */
public interface IGoodsService {
    PageResult<SpuBo> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key);
}
