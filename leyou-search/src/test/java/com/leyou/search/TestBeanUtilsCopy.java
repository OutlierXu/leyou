package com.leyou.search;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Spu;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.service.IGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author XuHao
 * @Title: TestBeanUtilsCopy
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1010:55
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBeanUtilsCopy {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private GoodsClient goodsClient;

    @Test
    public void test1(){

        PageResult<SpuBo> pageResult = goodsClient.querySpuByPage("H", true, 1, 100);

        List<SpuBo> items = pageResult.getItems();

        items.forEach(spuBo -> {
            Goods goods = null;
            try {
                goods = goodsService.buildGoods((Spu) spuBo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(goods);
        });


    }


}
