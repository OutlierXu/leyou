package leyou.com.auth.service.serviceImpl;

import com.leyou.auth.pojo.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.user.pojo.User;
import leyou.com.auth.client.UserClient;
import leyou.com.auth.config.JwtProperties;
import leyou.com.auth.service.IAuthService;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author XuHao
 * @Title: AuthService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1810:31
 */
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService implements IAuthService {


    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Override
    public String authentication(String username, String password) {

        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return null;
        }
        //1.查询用户是否存在
        User user = this.userClient.queryUser(username, password);
        if(user == null){
            return  null;
        }
        //2.生成token
        UserInfo userInfo = null;
        try {
            userInfo = new UserInfo(user.getId(), user.getUsername());
            return JwtUtils.generateToken(userInfo, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());
        } catch (Exception e) {
            logger.error("获取token失败！", e);
        }
        return null;
    }
}
