package com.leyou.search.Repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author XuHao
 * @Title: GoodsRepository
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/920:57
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
