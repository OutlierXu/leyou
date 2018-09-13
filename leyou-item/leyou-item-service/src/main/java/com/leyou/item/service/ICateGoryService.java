package com.leyou.item.service;


import com.leyou.item.pojo.Category;

import java.util.List;

public interface ICateGoryService {
    List<Category> queryCategoryListByParentId(Long pid);

    List<Category> queryCategoryListByBid(Long bid);

    List<Category> queryCategoryListByCids(List<Long> ids);
}
