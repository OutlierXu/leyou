package com.leyou.item.service.serviceImpl;

import com.leyou.item.mapper.ICategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.ICateGoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public List<Category> queryCategoryListByCids(List<Long> ids) {
        return categoryMapper.selectByIdList(ids);
    }


    @Override
    public List<Category> queryAllCategoryByCid3(Long cid3) {

        Category c3 = this.categoryMapper.selectByPrimaryKey(cid3);
        Category c2 = this.categoryMapper.selectByPrimaryKey(c3.getParentId());
        Category c1 = this.categoryMapper.selectByPrimaryKey(c2.getParentId());

        return Arrays.asList(c1,c2,c3);
    }
}


