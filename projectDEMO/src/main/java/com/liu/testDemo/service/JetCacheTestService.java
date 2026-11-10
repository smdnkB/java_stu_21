package com.liu.testDemo.service;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class JetCacheTestService {

    // 缓存方法的执行结果
    // expire 远程缓存过期时间
    // localExpire 本地缓存过期时间
    @Cached(cacheType = CacheType.BOTH,name = "test-cache",expire = 60*60*24,localExpire = 5,timeUnit = TimeUnit.SECONDS)
    public String jetCacheTest(){
        return LocalDateTime.now().toString();
    }
}
