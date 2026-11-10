package com.liu.testMoudel.controller;

import com.liu.testMoudel.service.TestService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @DubboReference // 远程服务
    private TestService testService;
    @GetMapping("testApi")
    public String testApi(){
        return testService.testApi();
    }
}
