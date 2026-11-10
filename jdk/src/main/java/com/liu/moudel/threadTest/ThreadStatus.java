package com.liu.moudel.threadTest;

/**
 * 线程状态
 */
public class ThreadStatus {

    // 线程的生命周期
    // new Thread()      new 初始状态
    // thread.start()    runnable  运行状态
    // wait  join  park  waiting 等待状态
    // park  sleep       timed_waiting 超时等待
    // synchronized      blocked 阻塞
    // thread.stop()     终止  不建议这种终止方式  run方法执行完毕 自动终止(条件判断 主动结束run方法, 可以优雅关闭线程)
}
