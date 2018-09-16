package com.leyou.search.listener;

import com.leyou.search.service.IGoodsService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author XuHao
 * @Title: GoodsListener
 * @ProjectName leyou
 * @Description: 监听消息队列，执行索引库的增删改操作
 * @date 2018/9/1520:37
 */
@Component
public class GoodsListener {

    @Autowired
    private IGoodsService goodsService;

    /**
     * 监听"LEYOU.CREATE.INDEX.QUEUE"队列，接收消息"item.insert","item.update"的消息，做新增/更新商品索引的操作
     * @param id
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.CREATE.INDEX.QUEUE",durable = "true"),
            exchange = @Exchange(value = "LEUYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void ListenToCreated(Long id) throws IOException {
        if(id == null){
            return;
        }
        // 创建或更新索引 ,由于是自动ACK所以此处异常抛出
        this.goodsService.createIndex(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.DELETE.INDEX.QUEUE",durable = "true"),
            exchange = @Exchange(value = "LEUYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = "item.delete"
    ))
    public void ListenToDelete(Long id){
        if(id == null){
            return;
        }
        // 删除索引 ,由于是自动ACK所以此处异常抛出
        this.goodsService.deleteIndex(id);
    }

}
