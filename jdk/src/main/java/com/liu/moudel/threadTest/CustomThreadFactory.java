package com.liu.moudel.threadTest;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setUncaughtExceptionHandler((t,e)->{
            // 线程出现异常就会执行到这里面来
            System.out.println("====="+t.getName()+"======");
        });
        return thread;
    }
}
