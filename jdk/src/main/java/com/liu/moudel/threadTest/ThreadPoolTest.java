package com.liu.moudel.threadTest;

import com.liu.moudel.thread.MyThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2,4,20, TimeUnit.SECONDS,new LinkedBlockingDeque<>(10));

        // 20个任务提交给线程池执行
        // 线程池只有两个 worker , 扩容后4个worker
        // worker去 阻塞队列取任务, 取到就执行, 取不到就阻塞或者销毁
        for (int i = 0; i < 20; i++) {
            poolExecutor.execute(new MyThread());
        }

        //

    }
}
