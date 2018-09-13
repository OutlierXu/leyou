package com.leyou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author XuHao
 * @Title: ItemController
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1323:02
 */
@Controller
@RequestMapping("item")
public class ItemController {


    @GetMapping("{id}.html")
    public String toItemPage(@PathVariable("id")Long id, Model model){

        return "item";
    }


}
