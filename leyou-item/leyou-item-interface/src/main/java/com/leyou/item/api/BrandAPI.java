package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author XuHao
 * @Title: BrandAPI
 * @ProjectName leyou
 * @Description: brand模块对外接口
 * @date 2018/9/921:10
 */
@RequestMapping("brand")
public interface BrandAPI {


    /**
     * 根据条件分页查询品牌信息
     * @param key 模糊查询关键字
     * @param page 当前页码
     * @param rows  每页条数
     * @param sortBy 排序字段
     * @param desc 是否降序
     * @return
     */
    @RequestMapping("page")
    public PageResult<Brand> queryBrandByPage(@RequestParam(value = "key",required = false)String key,
                                                              @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                              @RequestParam(value = "rows",defaultValue = "5")Integer rows,
                                                              @RequestParam(value = "sortBy",required = false)String sortBy,
                                                              @RequestParam(value = "desc",defaultValue = "false")Boolean desc);


}
