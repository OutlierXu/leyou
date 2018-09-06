package com.leyou.item.service.serviceImpl;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.IBrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.IBrandService;
import com.sun.org.apache.xpath.internal.SourceTree;
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
public class BrandServiceImpl implements IBrandService {


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
    @Transactional(rollbackFor = Exception.class)
    public void saveBrand(Brand brand, List<Long> cids) {

        //新增品牌信息
        this.brandMapper.insertSelective(brand);

        for (Long cid : cids) {
            this.brandMapper.insertCategoryBrand(cid,brand.getId());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBrand(Brand brand, List<Long> cids) {

        //更新對應id的brand信息
        brandMapper.updateByPrimaryKey(brand);

        //刪除對應id下的category信息
        brandMapper.deleteCategoryBrandByBid(brand.getId());

        for (Long cid : cids) {
            System.out.println(cid);
            this.brandMapper.insertCategoryBrand(cid,brand.getId());
        }
        System.out.println("更新完畢！");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrand(long id) {

        //1.删除brand表的记录
        brandMapper.deleteByPrimaryKey(id);

        //2.查询brand_category下的對應id的记录
        int i = brandMapper.selectCategoryBrand(id);

        if (i > 0){
            //3.删除brand_category表的對應id下的category信息
            brandMapper.deleteCategoryBrandByBid(id);
            System.out.println(id+" 删除成功!");
        }
    }

}
