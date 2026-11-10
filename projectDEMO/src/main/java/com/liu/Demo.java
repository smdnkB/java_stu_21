package com.liu;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodCache(basePackages = "com.liu.testDemo.service")
public class Demo {
    public static void main(String[] args) {
        SpringApplication.run(Demo.class,args);
    }
}