package com.leyou.item.service.serviceImpl;

import com.leyou.item.mapper.ISpecGroupMapper;
import com.leyou.item.mapper.ISpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.ISpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<SpecParam> selectSpecParam(Long gid) {

        Example example = new Example(SpecParam.class);
        example.createCriteria().andEqualTo("groupId", gid);

        List<SpecParam> specParamList = specParamMapper.selectByExample(example);
        return specParamList;
    }
}
