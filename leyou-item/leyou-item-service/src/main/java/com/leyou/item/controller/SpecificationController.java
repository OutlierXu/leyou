package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.ISpecificationService;
import org.apache.ibatis.annotations.Delete;
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

    /**
     * 根据cid3查询规格参数组即组下所有的规格参数
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> selectSpecGroup(@PathVariable("cid") Long cid){

        List<SpecGroup> specGroupList = specificationService.selectSpecGroup(cid);

        if(!CollectionUtils.isEmpty(specGroupList)){
            return ResponseEntity.ok(specGroupList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("group")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroup specGroup){

        boolean flag = specificationService.saveSpecGroup(specGroup);
        if(flag){

            //返回新增成功状态码
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup){

        boolean flag = specificationService.updateSpecGroup(specGroup);
        if(flag){

            //返回新增成功状态码
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteSpecGroup(@PathVariable("id")long id){
        System.out.println(id);

        boolean flag = specificationService.deleteSpecGroup(id);
        if(flag){

            //返回新增成功状态码
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> selectSpecParamByGid(@RequestParam(value = "gid",required = false) Long gid,
                                                                @RequestParam(value = "cid",required = false) Long cid,
                                                                @RequestParam(value = "searching",required = false) Boolean searching,
                                                                @RequestParam(value = "generic",required = false) Boolean generic
                                                                ){

        List<SpecParam> specParamList = specificationService.selectSpecParam(gid,cid,searching,generic);

        if(!CollectionUtils.isEmpty(specParamList)){
            return ResponseEntity.ok(specParamList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



    @PostMapping("param")
    public ResponseEntity<Void> saveSpecParam(@RequestBody SpecParam Param){



        boolean flag = specificationService.saveSpecParam(Param);
        if(flag){

            //返回新增成功状态码
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @PutMapping("param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam Param){

        System.out.println(Param);

        boolean flag = specificationService.updateSpecParam(Param);
        if(flag){

            //返回新增成功状态码
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("id")long id){



        boolean flag = specificationService.deleteSpecParam(id);
        if(flag){

            //返回新增成功状态码
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
