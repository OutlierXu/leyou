package com.leyou.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author XuHao
 * @Title: SmsListener
 * @ProjectName leyou
 * @Description: 监听队列中的消息，给用户发送短信
 * @date 2018/9/1615:31
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {


    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties properties;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value ="LEYOU.SMS.QUEUE",durable = "true"),
            exchange = @Exchange(value = "LEYOU.SMS.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"sms.verify.code"}))
    public void listenToSendMessage(Map<String,String> msg) throws ClientException {

        if(msg.isEmpty()){
            return;
        }

        String phone = msg.get("phone");
        String code = msg.get("code");

        if(StringUtils.isBlank(phone) || StringUtils.isBlank(code)){
            return;
        }
        //发送短信
        this.smsUtils.sendSms(phone, code, properties.getSignName(), properties.getVerifyCodeTemplate());

    }
}
