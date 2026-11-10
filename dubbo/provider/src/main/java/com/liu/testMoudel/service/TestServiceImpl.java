package com.liu.testMoudel.service;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService // 将接口发布为 dubbo 服务
public class TestServiceImpl implements TestService {
    @Override
    public String testApi() {
        return "test";
    }
}
