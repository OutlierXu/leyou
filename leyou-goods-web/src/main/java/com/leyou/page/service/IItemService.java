package com.leyou.page.service;

import java.util.Map;

/**
 * @author XuHao
 * @Title: IItemService
 * @ProjectName leyou
 * @Description: 页面数据加载类
 * @date 2018/9/149:09
 */
public interface IItemService {
    /**
     * 根据spu的id获取商品详情页model数据，数据结构Map<String,Object>
     * @param id 当前详情页面spu的id
     * @return model数据，数据结构Map<String,Object>
     */
    Map<String,Object> loadModel(Long id);
}
