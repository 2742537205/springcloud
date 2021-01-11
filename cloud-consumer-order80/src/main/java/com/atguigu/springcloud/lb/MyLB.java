package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyLB implements LoadBalancer {

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstanceList) {
        return null;
    }
}
