package com.leyou.item.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import com.leyou.item.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
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

    @Autowired
    private ISpuDetailMapper spuDetailMapper;

    @Autowired
    private ISkuMapper skuMapper;

    @Autowired
    private IStockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;



    @Override
    public PageResult<SpuBo> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (saleable != null){
            criteria.andEqualTo("saleable", saleable);
        }
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
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

    @Override
    @Transactional
    public Boolean addGoods(SpuBo spuBo) {

        //1.基於表關係，先保存spu表
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuBo, spu);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setValid(true);
        spu.setSaleable(true);

        System.out.println("新增商品spu信息" + spu);

        spuMapper.insertSelective(spu);

        //2.保存spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());

        spuDetailMapper.insertSelective(spuDetail);

        //3.保存sku
        saveSku(spuBo, spu.getId());

        //4.发送消息到队列
        sendMsg(spuBo.getId(),"insert");

        return true;
    }

    @Override
    public Spu querySpuById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Sku> querySkuListBySpuId(Long id) {
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId", id);
        List<Sku> skuList = skuMapper.selectByExample(example);
        for (Sku sku : skuList) {
            Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
            sku.setStock(stock.getStock());
        }
        return skuList;
    }

    @Override
    public SpuDetail querySpuDetailById(Long id) {
        return spuDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public Boolean updateGoods(SpuBo spuBo) {
        //1.先刪除存在的sku表（因為1：n經過修改sku，之前存在的sku可能已經不存在了）
        Long id = spuBo.getId();

        deleteSkuAndStock(id);

        //2.新增sku和庫存
        this.saveSku(spuBo, id);

        //3.更新spu
        spuBo.setLastUpdateTime(new Date());
        spuMapper.updateByPrimaryKeySelective(spuBo);

        //4.更新spuDetail
        spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());

        //5.发送消息到队列
        sendMsg(id,"update");


        return true;
    }


    @Override
    @Transactional
    public Boolean deleteSpu(long id) {
        //1.先刪除存在的sku表
        deleteSkuAndStock(id);

        //2.刪除spuDetail記錄
        spuDetailMapper.deleteByPrimaryKey(id);

        //3.刪除spu表
        spuMapper.deleteByPrimaryKey(id);

        //4.发送消息到队列
        sendMsg(id,"delete");

        return true;
    }

    @Override
    public Sku querySkuBySkuId(Long skuId) {
        return this.skuMapper.selectByPrimaryKey(skuId);
    }

    /**
     * 保存sku及對應庫存
     * @param spuBo
     * @param id 對應的SPU的id
     */
    private void saveSku(SpuBo spuBo, Long id) {
        for (Sku sku : spuBo.getSkus()) {

            sku.setSpuId(id);
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());

            skuMapper.insertSelective(sku);

            //更新庫存表（sku的副表）
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());

            stockMapper.insertSelective(stock);
        }
    }

    /**
     * 刪除sku及對應庫存
     * @param id 對應的SPU的id
     */
    private void deleteSkuAndStock(Long id) {
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId", id);
        List<Sku> skuList = skuMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(skuList)){

            //刪除以前的庫存
            List<Long> ids = skuList.stream().map(sku -> sku.getId()).collect(Collectors.toList());
            Example stockExample = new Example(Stock.class);
            stockExample.createCriteria().andIn("skuId", ids);
            stockMapper.deleteByExample(stockExample);

            //刪除以前的sku記錄
            skuMapper.deleteByExample(example);
        }
    }

    /**
     * 发送消息到消息队列
     * @param id 消息携带的商品id
     * @param type 消息的类型（区别增删改方法）
     */
    public void sendMsg(Long id, String type){

        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
    }

}
