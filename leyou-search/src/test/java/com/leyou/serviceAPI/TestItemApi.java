package com.leyou.serviceAPI;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author XuHao
 * @Title: testItemApi
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/921:19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestItemApi {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private CategoryClient categoryClient;


    @Test
    public void testBrandAPI(){

        PageResult<Brand> result = brandClient.queryBrandByPage("米", 1, 5, null, false);

        result.getItems().forEach(System.out::println);

    }

    @Test
    public void testGoodsClient(){
        PageResult<SpuBo> result = goodsClient.querySpuByPage("小米", true, 1, 100);
        result.getItems().forEach(spuBo -> System.out.println(spuBo));


    }

    @Test
    public void testBrandAPI2(){
        Brand brand = brandClient.queryBrandByBid(2032L);
        System.out.println(brand);
    }

    @Test
    public void testCategoryAPI(){

        List<String> categoryNames = this.categoryClient.queryCategoryNamesByCids(Arrays.asList(61L, 67L, 69L));
        System.out.println(categoryNames);
    }



}
