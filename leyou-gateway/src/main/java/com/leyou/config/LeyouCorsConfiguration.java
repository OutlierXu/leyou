package com.leyou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author XuHao
 */
@Configuration
public class LeyouCorsConfiguration {

    @Bean
    public CorsFilter corsFilter(){
        //1.添加CORS配置信息
        CorsConfiguration configuration = new CorsConfiguration();
        //1.1.1s设置服务器信任域名
        configuration.addAllowedOrigin("http://manage.leyou.com");
        configuration.addAllowedOrigin("http://www.leyou.com");
        //1.2.是否发送cookie信息
        configuration.setAllowCredentials(true);
        //1.3.允许哪些请求跨域
        configuration.addAllowedMethod("*");
        //1.4.允许携带所有头信息
        configuration.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);

        //3.返回该corsfilter过滤器对象
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
