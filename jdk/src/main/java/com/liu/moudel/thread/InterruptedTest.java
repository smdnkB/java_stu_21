package com.liu.moudel.thread;

public class InterruptedTest  implements Runnable{

    @Override
    public void run() {

        // 判断当前线程是否有中断标记
        while (true){
            try {
                Thread.sleep(1000);
                System.out.println( !Thread.currentThread().isInterrupted());
//                if (! Thread.currentThread().isInterrupted()) return;
            } catch (InterruptedException e) { // 捕获到这个异常中断也会复位 , 和 Thread.interrupted()效果一样
                System.out.println("error");
                System.out.println("中断复位:"+Thread.currentThread().isInterrupted()); // 获取中断标记 并复位
                return;
//                线程主动 interrupt 会触发这个异常, 这里不能捕获, 通过while判断
//                throw new RuntimeException(e);
            }
        }
    }
}
