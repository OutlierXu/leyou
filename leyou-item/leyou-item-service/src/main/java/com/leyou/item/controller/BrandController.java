package com.leyou.item.controller;


import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandByBid(@PathVariable("id")Long id){

        Brand brand = brandService.queryBrandByBid(id);

        if (brand == null){

            //查询品牌数为空
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(brand);
    }

    @GetMapping
    public ResponseEntity<List<Brand>> queryBrandByBids(@RequestParam("bids")List<Long> bids){
        List<Brand> brandList = brandService.queryBrandByBids(bids);

        if(CollectionUtils.isEmpty(brandList)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(brandList);
    }

    @RequestMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(@RequestParam(value = "key",required = false)String key,
                                                              @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                              @RequestParam(value = "rows",defaultValue = "5")Integer rows,
                                                              @RequestParam(value = "sortBy",required = false)String sortBy,
                                                              @RequestParam(value = "desc",defaultValue = "false")Boolean desc){

        PageResult<Brand> result = brandService.queryBrandByPageAndSort(page,rows,sortBy,desc, key);

        if (result == null || result.getItems().size() == 0){

            //查询品牌数为空
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);


    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand,@RequestParam("cids") List<Long> cids){

        this.brandService.saveBrand(brand,cids);

        //返回新增成功状态码
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand,@RequestParam("cids") List<Long> cids){

        try {
            boolean flag = this.brandService.updateBrand(brand,cids);

            if(flag){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回異常狀態码
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBrand(@RequestParam("id")long id){

        this.brandService.deleteBrand(id);

        //返回删除成功状态码
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> updateBrandByCid3(@PathVariable("cid")Long cid){

        List<Brand> brandList = this.brandService.updateBrandByCid3(cid);

        if(!CollectionUtils.isEmpty(brandList)){
            return ResponseEntity.ok(brandList);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
