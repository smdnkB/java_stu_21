package com.liu.moudel.threadTest;

import com.liu.moudel.thread.InterruptedTest;

/**
 * 中断标记
 */
public class Interrupt {
    public static void main(String[] args) throws InterruptedException {

        // 优雅关闭线程  -- interrupted
        Thread test = new Thread(new InterruptedTest());
        test.start();
        Thread.sleep(3000);
        test.interrupt(); // 主动 interrupt 标记线程中断
        Thread.sleep(1000);
        System.out.println("中断复位:"+Thread.interrupted()); // 获取中断标记 并复位
        System.out.println("重新获取中断:"+Thread.currentThread().isInterrupted()); // 获取中断标记 并复位
    }
}
