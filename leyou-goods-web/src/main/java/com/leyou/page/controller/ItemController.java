package com.leyou.page.controller;

import com.leyou.page.service.IItemHtmlService;
import com.leyou.page.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author XuHao
 * @Title: ItemController
 * @ProjectName leyou
 * @Description: 如果nginx中没有静态页面，请求被路由到此服务，该服务会根据spuid去数据库加载数据模型并执行页面静态化(保存至ngnix中的html目录中)，
 * @date 2018/9/1323:02
 */
@Controller
@RequestMapping("item")
public class ItemController {


    @Autowired
    private IItemService itemService;

    @Autowired
    private IItemHtmlService itemHtmlService;

    @GetMapping("{id}.html")
    public String toItemPage(@PathVariable("id")Long id, Model model){

        //根据spuid加载数据模型
        Map<String,Object> modelMap = itemService.loadModel(id);
        model.addAllAttributes(modelMap);

        //线程池执行页面静态化==>保存至ngnix中的html目录中
        this.itemHtmlService.asyncExcute(id);

        return "item";
    }


}
