package com.liu.moudel.threadTest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁  (支持公平锁和 非公平锁) new ReentrantLock(true); 默认非公平锁
 * 源码逻辑
 *    加锁流程: 多个线程并发 通过CAS 保证只有一个线程拿到锁, 其他线程加入双向链表并且 park
 *    释放流程: 释放当前线程 并unpark 唤醒 链表的head节点的下一个 线程
 *
 *    非公平的原因: 线程1 结束 唤醒线程2 ,但是新建的线程4不在队列里且刚好抢到了锁. 导致线程2 继续等待
 *    公平锁: 线程抢夺锁先判断线程是不是在排队队列且在head下一个节点
 *
 *    AQS: abstract Queued Synchronizer 内部维护了一个node的双向链表 用state标记线程状态 , 提供了公用方法的实现和 一些模板方法
 */
public class ReentrantLockTest {
    ReentrantLock reentrantLock = new ReentrantLock(true);
    int i = 0;

    public void incr(){
        reentrantLock.lock();
        i++;
        reentrantLock.unlock(); // 数据修改完之后释放锁
    }

    public void incrTry(){
        reentrantLock.lock();
        i++;
        reentrantLock.unlock(); // 数据修改完之后释放锁
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest test = new ReentrantLockTest();

        // 5个线程同时 对 i 加1000次
        // 预期 i= 5000
        // 实际 i <= 5000
        Thread[] threads = new Thread[5];
        for (int i = 0; i <5; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    test.incr();
                }
            });
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.start();
        }

        Thread.sleep(3000);
        System.out.println(test.i);


    }
}
