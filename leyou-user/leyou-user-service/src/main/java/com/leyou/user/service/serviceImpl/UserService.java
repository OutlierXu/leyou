package com.leyou.user.service.serviceImpl;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.pojo.User;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.service.IUserService;
import com.leyou.user.utils.CodecUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
            System.out.println(code);
            this.redisTemplate.opsForValue().set(KEY_PREFIX + phone,code ,5 , TimeUnit.MINUTES);

            return true;
        } catch (AmqpException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Boolean register(User user, String code) {

        String redisCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        System.out.println("redisCode:" + redisCode);
        System.out.println("Code:" + code);
        //1.校验验证码
        if(!StringUtils.equals(redisCode, code)){
            return false;
        }
        //2. 生成加密密码
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        user.setCreated(new Date());
        //3.保存用户
        Boolean boo = this.userMapper.insertSelective(user) == 1;
        //4.如果保存成功，删除redis

        if(boo){
            this.redisTemplate.delete(KEY_PREFIX + user.getPhone());
        }
        return boo;
    }

    @Override
    public User queryUser(String username, String password) {

        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);

        if(user == null || !StringUtils.equals(user.getPassword(), CodecUtils.md5Hex(password, user.getSalt()))){
            //查询不到，或者密码不匹配
            return null;
        }
        return user;
    }
}
