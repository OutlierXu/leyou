package com.leyou.item.service.serviceImpl;

import com.leyou.item.mapper.ICategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.ICateGoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author XuHao
 */
@Service

public class CategoryServiceImpl implements ICateGoryService {

    @Autowired
    private ICategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryListByParentId(Long pid) {

        Category record = new Category();
        record.setParentId(pid);

        List<Category> categoryList = categoryMapper.select(record);

        return categoryList;

    }

    @Override
    public List<Category> queryCategoryListByBid(Long bid) {




        List<Category> categoryList = categoryMapper.queryCategoryListByBid(bid);


        return categoryList;
    }
}


