package com.leyou.page.service.serviceImpl;

import com.leyou.page.service.IItemHtmlService;
import com.leyou.page.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author XuHao
 * @Title: ItemHtmlService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1511:04
 */
@Service
public class ItemHtmlService implements IItemHtmlService {



    @Autowired
    private ItemService itemService;

    @Autowired
    private TemplateEngine engine;

    /**
     * 创建html静态页面
     * @param id
     */
    @Override
    public void createHtml(Long id) {

        PrintWriter writer = null;

        try {

        //创建thymeleaf上下文对象,并设置数据模型
        Context context = new Context();
        Map<String,Object> map = itemService.loadModel(id);
        context.setVariables(map);

        //创建输出流对象
        File file = new File("D:\\Program Files\\nginx-1.14.0\\html\\item\\" + id + ".html");
        writer = new PrintWriter(file);
        //输出静态化页面
        this.engine.process("item", context, writer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 新建线程处理页面静态化
     * @param id
     */
    @Override
    public void asyncExcute(Long id) {

        ThreadUtils.execute(()->createHtml(id));
    }
}
