package com.leyou.serviceAPI;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.search.client.BrandClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


    @Test
    public void testBrandAPI(){

        PageResult<Brand> result = brandClient.queryBrandByPage("米", 1, 5, null, false);

        result.getItems().forEach(System.out::println);

    }


}
