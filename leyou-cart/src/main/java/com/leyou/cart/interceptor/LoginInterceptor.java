package com.leyou.cart.interceptor;

import com.leyou.auth.pojo.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XuHao
 * @Title: LoginInterceptor
 * @ProjectName leyou
 * @Description: 读取配置文件，定义一个登陆请求的拦截器LoginInterceptor（检查请求是否携带令牌token，有且解析正确则放行，无则被拦截）
 *               额外功能：提供一个线程域TL_USER_INFO：保存解析到的用户信息userInfo 及获取用户信息的静态方法getLoginUser()
 * @date 2018/9/1916:20
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private JwtProperties jwtProperties;

    private static final ThreadLocal<UserInfo> TL_USER_INFO = new ThreadLocal<>();



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //获取token并解析校验
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());
        if(StringUtils.isBlank(token)){
            //未登录返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        try {
            UserInfo user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            //放入线程域
            TL_USER_INFO.set(user);

            return true;
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {
        //清空线程域
        TL_USER_INFO.remove();
    }

    public static UserInfo getLoginUser(){
        return TL_USER_INFO.get();
    }

}
