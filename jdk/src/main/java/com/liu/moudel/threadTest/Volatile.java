package com.liu.moudel.threadTest;

/**
 * 并发编程要考虑三大问题
 * 原子性 : 要保证多个操作 一致
 * 有序性 : 命令要有序  有指令重排的问题
 * 可见性 : A线程使用的变量 被 B线程修改了 正常情况下A线程不可见修改结果. 要解决可见性
 */

/**
 *
 */
public class Volatile {

    int i = 0;
    int i2 = 0;
    volatile int i3 = 0;

    /**
     * 原子性问题演示
     * i++ 分三部  load  add  store
     * 每一步都有可能被其他线程插进来影响结果
     */
    public void incr(){
        i++;
    }
    /**
     * 原子性问题修复
     * i++ 分三部  load  add  store
     * 每一步都有可能被其他线程插进来影响结果
     */
    public synchronized void incr2(){
        i2++;
    }
    public synchronized void incr3(){
        i3++;
    }
    public static void main(String[] args) throws InterruptedException {
        Volatile aVolatile = new Volatile();

        // 5个线程同时 对 i 加1000次
        // 预期 i= 5000
        // 实际 i <= 5000
        Thread[] threads = new Thread[5];
        for (int i = 0; i <5; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    aVolatile.incr();
                    aVolatile.incr2();
                    aVolatile.incr3();
                }
            });
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.start();
        }

        Thread.sleep(3000);
        System.out.println(aVolatile.i);
        System.out.println(aVolatile.i2);
        System.out.println(aVolatile.i3);
    }
}
