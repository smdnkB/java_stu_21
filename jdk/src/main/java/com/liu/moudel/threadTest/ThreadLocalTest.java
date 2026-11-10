package com.liu.moudel.threadTest;

public class ThreadLocalTest {

    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(()->0);

    public void incr(){
        threadLocal.set(threadLocal.get()+1);
        System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
    }
    public static void main(String[] args) {

        Thread[] threads = new Thread[5];
        ThreadLocalTest test = new ThreadLocalTest();

        for (int i = 0; i <5; i++) {
            Thread thread = new Thread(test::incr);
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.start();
        }

    }
}
