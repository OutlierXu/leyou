package com.leyou.search;

import com.leyou.search.Repository.GoodsRepository;
import com.leyou.search.pojo.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author XuHao
 * @Title: goodsElasticsearch
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/920:55
 */

/**
 * 向elasticSearch中导入商品信息数据
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsElasticsearch {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void insertGoodsData(){

        //1.创建goods索引库
        this.template.createIndex(Goods.class);

        //2.配置映射
        this.template.putMapping(Goods.class);


        //3.分批量保存数据库中的goods数据到索引库中（iterm微服务分页查询到的数据）
    }
}
