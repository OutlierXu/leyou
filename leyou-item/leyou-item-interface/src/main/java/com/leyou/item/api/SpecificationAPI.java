package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author XuHao
 * @Title: SpecificationAPI
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/921:58
 */
@RequestMapping("spec")
public interface SpecificationAPI {


    /**
     * 根据cid3查询规格参数组即组下所有的规格参数
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    List<SpecGroup> selectSpecGroup(@PathVariable("cid") Long cid);

    /**
     * 根据条件查询参数
     * @param gid 分组id
     * @param cid 分类id
     * @param searching 是否可搜索
     * @param generic 是否通用
     * @return
     */
    @GetMapping("params")
    List<SpecParam> selectSpecParamByGid(@RequestParam(value = "gid", required = false) Long gid,
                                         @RequestParam(value = "cid", required = false) Long cid,
                                         @RequestParam(value = "searching", required = false) Boolean searching,
                                         @RequestParam(value = "generic", required = false) Boolean generic);
}
