package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author XuHao
 * @Title: LeyouGoodWebApplication
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1322:40
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class LeyouGoodWebApplication {

    public static void main(String[] args) {

        SpringApplication.run(LeyouGoodWebApplication.class,args);
    }
}
