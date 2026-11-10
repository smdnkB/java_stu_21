package com.liu.moudel.thread;

public class MyThread extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println(Thread.currentThread().getName());
    }
}
