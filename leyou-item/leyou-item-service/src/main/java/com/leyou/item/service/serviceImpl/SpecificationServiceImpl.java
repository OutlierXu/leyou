package com.leyou.item.service.serviceImpl;

import com.leyou.item.mapper.ISpecGroupMapper;
import com.leyou.item.mapper.ISpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.ISpecificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author XuHao
 * @Title: SpecificationServiceImpl
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/610:28
 */
@Service
public class SpecificationServiceImpl implements ISpecificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecificationServiceImpl.class);

    @Autowired
    private ISpecGroupMapper specGroupMapper;

    @Autowired
    private ISpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> selectSpecGroup(Long cid) {

        Example example = new Example(SpecGroup.class);
        example.createCriteria().andEqualTo("cid", cid);

        List<SpecGroup> specGroupList = specGroupMapper.selectByExample(example);
        return specGroupList;
    }

    @Override
    public List<SpecParam> selectSpecParam(Long gid,Long cid, Boolean searching, Boolean generic) {

        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);

        List<SpecParam> specParamList = specParamMapper.select(record);
        return specParamList;
    }

    @Override
    public boolean saveSpecParam(SpecParam param) {

        return specParamMapper.insertSelective(param) > 0;
    }

    @Override
    public boolean updateSpecParam(SpecParam param) {
        return specParamMapper.updateByPrimaryKey(param) > 0;
    }

    @Override
    public boolean deleteSpecParam(long id) {
        return specParamMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean saveSpecGroup(SpecGroup specGroup) {

        //先查询数据库是否含有该分组
        Example example = new Example(specGroup.getClass());
        example.createCriteria().andEqualTo("name", specGroup.getName());
        List<SpecGroup> specGroupList = specGroupMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(specGroupList)){
            //查询空返回false
            LOGGER.info("{}-分组参数已经存在!", specGroup.getName());
            return false;
        }

        return specGroupMapper.insertSelective(specGroup) > 0;
    }

    @Override
    public boolean updateSpecGroup(SpecGroup specGroup) {
        return specGroupMapper.updateByPrimaryKey(specGroup) > 0;
    }

    @Override
    public boolean deleteSpecGroup(long id) {
        return specGroupMapper.deleteByPrimaryKey(id) > 0;
    }


}
