package com.leyou.item.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuBo;
import com.leyou.item.service.IGoodsService;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XuHao
 * @Title: GoodsServiceImpl
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/621:42
 */
@Service
public class GoodsServiceImpl implements IGoodsService {


    @Autowired
    private ISpuMapper spuMapper;

    @Autowired
    private IBrandMapper brandMapper;

    @Autowired
    private ICategoryMapper categoryMapper;


    @Override
    public PageResult<SpuBo> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (saleable != null){
            criteria.andEqualTo("saleable", saleable);
        }
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", key);
        }

        PageHelper.startPage(page, rows);

        List<Spu> spuList = spuMapper.selectByExample(example);
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spuList);

        //获取分页的spu信息
        List<Spu> spuListByPage = spuPageInfo.getList();

        //遍历获取每个spu商品的品牌、商品分类,封装为spuBo
        List<SpuBo> spuBoList = new ArrayList<>();
        spuListByPage.forEach(spu -> {

            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);

            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            System.out.println(brand);
            System.out.println("------------");
            spuBo.setBname(brand.getName());

            List<Category> categoryList = categoryMapper.selectByIdList(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
            List<String> names = categoryList.stream().map(category -> category.getName()).collect(Collectors.toList());

            spuBo.setCname(StringUtils.join(names, "-"));


            spuBoList.add(spuBo);

        });

        //封装为pageResult返回
        PageResult<SpuBo> pageResult = new PageResult<>(spuPageInfo.getTotal(), spuBoList);

        return pageResult;
    }
}
