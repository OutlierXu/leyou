package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * @author XuHao
 * @Title: ISpecificationService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/610:21
 */
public interface ISpecificationService {
    /**
     * 根据分类cid查询参数组信息（1：n）
     * @param cid 分类cid
     * @return 参数组信息集合List<SpecGroup>
     */
    List<SpecGroup> selectSpecGroup(Long cid);

    /**
     * 根据分组id查询参数列表信息（1：n）
     * @param gid 组别
     * @return 参数列表信息集合List<SpecParam>
     */
    List<SpecParam> selectSpecParam(Long gid);

    /**
     * 新增规格参数
     * @param param
     */
    boolean saveSpecParam(SpecParam param);

    /**
     * 更新规格参数
     * @param param
     * @return
     */
    boolean updateSpecParam(SpecParam param);

    /**
     * 删除规格参数
     * @param id
     * @return
     */
    boolean deleteSpecParam(long id);

    /**
     * 新增规格分组
     * @param specGroup
     * @return
     */
    boolean saveSpecGroup(SpecGroup specGroup);

    /**
     * 更新规格分组
     * @param specGroup
     * @return
     */
    boolean updateSpecGroup(SpecGroup specGroup);

    /**
     * 删除规格分组
     * @param id
     * @return
     */
    boolean deleteSpecGroup(long id);
}
