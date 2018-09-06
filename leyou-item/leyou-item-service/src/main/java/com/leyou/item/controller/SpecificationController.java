package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.ISpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XuHao
 * @Title: SpecificationController
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/610:15
 */
@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    public ISpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> selectSpecGroup(@PathVariable("cid") Long cid){

        List<SpecGroup> specGroupList = specificationService.selectSpecGroup(cid);

        if(!CollectionUtils.isEmpty(specGroupList)){
            return ResponseEntity.ok(specGroupList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> selectSpecParam(@RequestParam("gid") Long gid){

        List<SpecParam> specParamList = specificationService.selectSpecParam(gid);

        if(!CollectionUtils.isEmpty(specParamList)){
            return ResponseEntity.ok(specParamList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("param")
    public ResponseEntity<Void> saveUser(@RequestBody SpecParam Param){

        System.out.println(Param);

        return null;
    }


}
