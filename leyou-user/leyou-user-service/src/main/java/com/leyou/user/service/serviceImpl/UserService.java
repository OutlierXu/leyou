package com.leyou.user.service.serviceImpl;

import com.leyou.common.utils.NumberUtils;
import com.leyou.pojo.User;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author XuHao
 * @Title: UserService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1614:01
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    static final String KEY_PREFIX = "user:code:phone:";


    @Override
    public Boolean checkUserData(String data, Integer type) {

        if(StringUtils.isBlank(data)){
            return null;
        }
        User record = new User();

        switch (type){

            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                return null;
        }

        return userMapper.selectCount(record) == 0;
    }

    /**
     * 发送信息到消息队列
     * @param phone
     * @return
     */
    @Override
    public Boolean sendVerifyCode(String phone) {
        if(StringUtils.isBlank(phone)){
            return null;
        }
        //1.生产随机验证码
        String code = NumberUtils.generateCode(6);

        Map<String,String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code",code);

        try {
            //2.发送到mq消息队列中
            this.amqpTemplate.convertAndSend("LEYOU.SMS.EXCHANGE", "sms.verify.code", msg);

            //3.将验证码处理后保存到redis中
            this.redisTemplate.opsForValue().set(KEY_PREFIX + phone,code ,5 , TimeUnit.MINUTES);

            return true;
        } catch (AmqpException e) {
            e.printStackTrace();
            return false;
        }

    }
}
