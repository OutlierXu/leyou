package com.leyou.search;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.search.Repository.GoodsRepository;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.service.IGoodsService;
import joptsimple.internal.Rows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private IGoodsService goodsService;

    @Test
    public void insertGoodsData(){

        //1.创建goods索引库
        this.template.createIndex(Goods.class);

        //2.配置映射
        this.template.putMapping(Goods.class);


        //3.分批量保存数据库中的goods数据到索引库中（iterm微服务分页查询到的数据）
        int rows = 100;
        int page = 1;
        long size ;
        do{

            PageResult<SpuBo> result = goodsClient.querySpuByPage(null, true, page, rows);

            List<SpuBo> spuList = result.getItems();
            size = spuList.size();

            ArrayList<Goods> goodsList = new ArrayList<>();

            spuList.forEach(spuBo -> {

                Goods goods = null;
                try {
                    goods = this.goodsService.buildGoods(spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                goodsList.add(goods);

            });

            goodsRepository.saveAll(goodsList);

            page ++;

        }while (size == 100);


    }
}
