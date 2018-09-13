package com.leyou.item.service;


import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

public interface IBrandService {

    PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key);

    void saveBrand(Brand brand, List<Long> cids);

    boolean updateBrand(Brand brand, List<Long> cids);

    /**
     * 根据id删除brand
     * @param id
     */
    void deleteBrand(long id);

    /**
     * 根据三级属性查询品牌
     * @param cid
     * @return
     */
    List<Brand> updateBrandByCid3(Long cid);

    /**
     * 根据bid查询品牌
     * @param id
     * @return
     */
    Brand queryBrandByBid(Long id);

    /**
     * 批量获取brand
     * @param bids
     * @return
     */
    List<Brand> queryBrandByBids(List<Long> bids);
}
