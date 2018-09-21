package com.leyou.filter;

import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.config.FilterProperties;
import com.leyou.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XuHao
 * @Title: LoginFilter
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1821:56
 */
@Component
@EnableConfigurationProperties({JwtProperties.class,FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterProperties filterProperties;

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {

        //1.获取请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //2.获取request
        HttpServletRequest request = requestContext.getRequest();

        //3.获取当前的请求路径
        String requestURL = request.getRequestURL().toString();

        //4.遍历白名单
        for (String allowPath : this.filterProperties.getAllowPaths()) {
            if(requestURL.contains(allowPath)){
                //如果请求路径在白名单中，则不拦截
                return false;
            }
        }
        //其余路径的请求拦截
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        //1.获取请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //2.获取request
        HttpServletRequest request = requestContext.getRequest();
        //3.获取token
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        //4.校验权限
        try {
            //校验通过，什么都不做，即放行
            JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
        } catch (Exception e) {
            //校验出现异常，返回403
            logger.error("权限不通过，被网关拦截！", e);
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }
        return null;
    }
}
