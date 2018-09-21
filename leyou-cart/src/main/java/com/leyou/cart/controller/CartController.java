package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XuHao
 * @Title: CartController
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1911:20
 */
@Controller
public class CartController {

    @Autowired
    private ICartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){

        Boolean boo = this.cartService.addCart(cart);
        if(boo){
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 批量保存cart到redis中
     * @param cartList
     * @return
     */
    @PostMapping("list")
    public ResponseEntity<Void> addCartList(@RequestBody List<Cart> cartList){
        Boolean boo = this.cartService.addCartList(cartList);
        if(boo){
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("list")
    public ResponseEntity<List<Cart>> queryCartList(){
        List<Cart> carts = this.cartService.queryCartList();

        if(CollectionUtils.isEmpty(carts)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(carts);
    }

    @PutMapping
    public ResponseEntity<Void> updateCart(@RequestBody Cart cart){
        Boolean boo = this.cartService.updateCart(cart);
        if(boo == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{skuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("skuId")Long skuId){
        this.cartService.deleteCart(skuId);
        return ResponseEntity.ok().build();
    }
}
