package com.liu.testDemo.controller;

import com.liu.testDemo.service.JetCacheTestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JetCacheTestController {
    @Resource
    private JetCacheTestService jetCacheTestService;

    @GetMapping("jetCacheTest")
    public String jetCacheTest(){
        return jetCacheTestService.jetCacheTest();
    }
}
