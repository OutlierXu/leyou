package leyou.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author XuHao
 * @Title: LeyouAuthApplication
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/189:14
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouAuthApplication.class,args);
    }
}
