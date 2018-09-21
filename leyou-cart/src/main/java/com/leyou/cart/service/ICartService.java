package com.leyou.cart.service;

import com.leyou.cart.pojo.Cart;

import java.util.List;

/**
 * @author XuHao
 * @Title: ICartService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1921:28
 */
public interface ICartService {
    Boolean addCart(Cart cart);

    List<Cart> queryCartList();

    /**
     *根据skuid删除redis中的购物车信息
     * @param skuId
     */
    void deleteCart(Long skuId);

    /**
     * 更改redis中的购物车信息
     * @param cart
     * @return
     */
    Boolean updateCart(Cart cart);

    /**
     * 批量保存cart到redis中
     * @param cartList
     * @return
     */
    Boolean addCartList(List<Cart> cartList);
}
