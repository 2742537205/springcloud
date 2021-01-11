package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id){
        int o = 10/0;
        return "线程池"+Thread.currentThread( ).getName()+"paymentInfo_OK,id："+id+"\t"+"O(∩_∩)O哈哈~";
    }


    //判断如果调用TimeOut方法超过3秒的话，则调用TimeOutHandler方法，并返回给调用的controller
    @HystrixCommand(fallbackMethod = "payment_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "6000")
    })
    public String paymentInfo_TimeOut(Integer id){
        return "线程池"+Thread.currentThread().getName()+"paymentInfo_TimeOut,id："+id+"\t"+"O(∩_∩)O哈哈~";
    }

    public String payment_TimeOutHandler(Integer id){
        return "服务繁忙";
    }


    //=====服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled", value="true"),  // 是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),  //请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"), // 时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60"),  // 失败率达到多少后跳闸
            /**
             * 整体意思：在10秒内，请求次数达到10次并且失败率达到60的话，那么会开启断路器，
             * 开启断路器后,会对主逻辑熔断并且 hystrix会开启休眠时间窗，在这个时间窗内，降级逻辑将替代主逻辑进行执行，
             * 当休眠时间窗到期后，断路器变成半开状态，此时会释放一次请求到原来的主逻辑上，如果执行成功那么这个断路器将闭合，主逻辑恢复，
             * 如果这次请求依旧有问题，那么断路器继续打开状态，并且休眠时间窗重新计时。
             *
             * */
    })
    public String paymentCircuitBreaker(Integer id){
        if(id < 0){
            throw new RuntimeException("*****id，不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(Integer id){
        return "id 不能为负数，请稍后再试....";
    }

}
