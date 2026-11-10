package com.liu.moudel.threadTest;

import com.liu.moudel.thread.MyCallable;
import com.liu.moudel.thread.MyThread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的三种方法
 */
public class CreateThread3Fun {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 线程创建的三种方式  thread  runnable  callable
        new MyThread().start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName());
        },"重写 runnable 线程").start();

        FutureTask futureTask = new FutureTask(new MyCallable());
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
