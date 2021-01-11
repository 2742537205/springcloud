
/**
 *  Consul 注册中心的订单消费者
 * */

package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsulMain80 {
    public static void main(String[] args){
        SpringApplication.run(OrderConsulMain80.class,args);
    }
}
