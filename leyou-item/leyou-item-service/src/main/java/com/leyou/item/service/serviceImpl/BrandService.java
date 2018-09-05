package com.leyou.item.service.serviceImpl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.IBrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.IBrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author XuHao
 */
@Service
public class BrandService implements IBrandService {


    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {

        Example example = new Example(Brand.class);

        //如果模糊查询的关键字不为空
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().andLike("name","%" + key + "%").orEqualTo("letter",key);
        }

        //如果排序条件不为空
        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy + (desc ? " desc " : " asc "));
        }

        //开启分页查询
        PageHelper.startPage(page,rows);

        //通用mapper执行查询
        List<Brand> brandList = this.brandMapper.selectByExample(example);

        //封装为pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);


        PageResult<Brand> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());

        return pageResult;
    }

    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {

        //新增品牌信息
        this.brandMapper.insertSelective(brand);

        for (Long cid : cids) {
            this.brandMapper.insertCategoryBrand(cid,brand.getId());
        }

    }


}
