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


    @GetMapping("groups/{cid}")
    List<SpecGroup> selectSpecGroup(@PathVariable("cid") Long cid);

    /**
     * 根据条件查询参数
     * @param gid
     * @param cid
     * @param searching
     * @param generic
     * @return
     */
    @GetMapping("params")
    List<SpecParam> selectSpecParamByGid(@RequestParam(value = "gid", required = false) Long gid,
                                         @RequestParam(value = "cid", required = false) Long cid,
                                         @RequestParam(value = "searching", required = false) Boolean searching,
                                         @RequestParam(value = "generic", required = false) Boolean generic);
}
