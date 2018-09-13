package com.leyou.search.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;

import java.io.IOException;

/**
 * @author XuHao
 * @Title: IGoodsService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1114:32
 */
public interface IGoodsService {

    /**
     * 按关键词搜索分页商品信息
     * @param request
     * @return
     */
    PageResult<Goods> search(SearchRequest request);

    /**
     * 将查询到的每个Spu数据==>为Goods对象，以便存入索引库
     * @param spu
     * @return 封装好的goods对象
     */
    public Goods buildGoods(Spu spu) throws IOException;
}
