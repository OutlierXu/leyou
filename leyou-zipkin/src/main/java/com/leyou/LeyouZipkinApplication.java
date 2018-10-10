package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

/**
 * @author XuHao
 * @Title: LeyouZipkinApplication
 * @ProjectName leyou
 * @Description: 创建zipkin服务器
 * @date 2018/9/2211:25
 */
@SpringBootApplication
@EnableZipkinServer
@EnableDiscoveryClient
public class LeyouZipkinApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouZipkinApplication.class,args);
    }
}
