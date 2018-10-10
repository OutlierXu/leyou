package com.leyou.order.pojo;

import com.leyou.common.pojo.PageResult;

import java.util.List;

/**
 * @author XuHao
 * @Title: OrderResult
 * @ProjectName leyou
 * @Description: 封装订单,订单详情对象
 * @date 2018/9/2510:12
 */
public class OrderResult extends PageResult<Order> {

    //字符串类型的订单ID
    private String OrderList;

    public OrderResult(long total, Integer totalPage, List<Order> items, String orderList) {
        super(total, totalPage, items);
        OrderList = orderList;
    }

    public OrderResult(String orderList) {
        OrderList = orderList;
    }

    public OrderResult(long total, List<Order> items, String orderList) {
        super(total, items);
        OrderList = orderList;
    }

    public String getOrderList() {
        return OrderList;
    }

    public void setOrderList(String orderList) {
        OrderList = orderList;
    }
}