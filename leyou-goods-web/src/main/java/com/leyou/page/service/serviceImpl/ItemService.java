package com.leyou.page.service.serviceImpl;

import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecificationClient;
import com.leyou.page.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author XuHao
 * @Title: ItemService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/149:10
 */
@Service
public class ItemService implements IItemService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;


    @Override
    public Map<String, Object> loadModel(Long id) {


        //获取spu
        Spu spu = this.goodsClient.querySpuById(id);

        //获取spuDtail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailById(id);

        //获取skus
        List<Sku> skus = this.goodsClient.querySkuListBySpuId(id);

        //获取品牌
        Brand brand = this.brandClient.queryBrandByBid(spu.getBrandId());

        //获取各级分类,并对数据进行转化=>Map<"id":"name">
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> categoryNames = this.categoryClient.queryCategoryNamesByCids(cids);

        ArrayList<Map<String, Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> category = new HashMap<>();

            category.put("id",cids.get(i));
            category.put("name", categoryNames.get(i));

            categories.add(category);
        }

        //获取specGroups
        List<SpecGroup> specGroups = this.specificationClient.selectSpecGroup(spu.getCid3());

        //查询特有规格参数 转为Map<id,name>的形式
        List<SpecParam> specParamList = this.specificationClient.selectSpecParamByGid(null, spu.getCid3(), null, false);
        Map<Long, String> paramMap = new HashMap<>();
        specParamList.forEach(specParam -> {
            paramMap.put(specParam.getId(), specParam.getName());
        });

        Map<String, Object> map = new HashMap<>();
        map.put("spu", spu);
        map.put("spuDetail", spuDetail);
        map.put("skus", skus);
        map.put("brand", brand);
        map.put("categories", categories);
        map.put("groups", specGroups);
        map.put("params", paramMap);
        return map;
    }
}
