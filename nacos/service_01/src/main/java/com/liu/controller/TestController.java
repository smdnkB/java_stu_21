package com.liu.controller;

import com.liu.config.NacosConfig;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope // 开启nacos配置动态刷新
public class TestController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private NacosConfig nacosConfig;

    @Value("${flg:0}") // 去指定的配置文件中找值
    private Integer flg;
    @GetMapping("/testApi")
    public String testApi(){
        // Ribbon 不支持 service_02 这种下划线访问，会报错
        String forObject = restTemplate.getForObject("http://service02/testApi", String.class);
        return "ces1:"+forObject;
    }
    @GetMapping("/val")
    public String getConfigVal(){
        return flg+"-"+nacosConfig.getMessage();
    }
}
