package com.leyou.page.listener;

import com.leyou.page.service.IItemHtmlService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author XuHao
 * @Title: GoodsListener
 * @ProjectName leyou
 * @Description: 监听消息队列，执行静态页面的增删改
 * @date 2018/9/1521:11
 */
@Component
public class GoodsListener {

    @Autowired
    private IItemHtmlService itemHtmlService;

    /**
     * 监听"LEYOU.CREATE.HTML.QUEUE"消息队列的"item.insert","item.update"消息，执行创建静态页面的方法
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value = "LEYOU.CREATE.HTML.QUEUE",durable = "true"),
            exchange = @Exchange(value = "LEUYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void listenToCreateHtml(Long id){

        if(id == null){
            return;
        }
        this.itemHtmlService.createHtml(id);
    }

    /**
     * 监听"LEYOU.CREATE.HTML.QUEUE"消息队列的"item.delete",消息，执行删除静态页面的方法
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value = "LEYOU.DELETE.HTML.QUEUE",durable = "true"),
            exchange = @Exchange(value = "LEUYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = "item.delete"
    ))
    public void listenToDeleteHtml(Long id){
        if(id == null){
            return;
        }
        this.itemHtmlService.deleteHtml(id);
    }
}
