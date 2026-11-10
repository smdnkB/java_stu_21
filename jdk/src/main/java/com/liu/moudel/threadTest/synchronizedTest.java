package com.liu.moudel.threadTest;

/**
 * 重量级同步锁
 */
public class synchronizedTest {

    public static void main(String[] args) {

        /**
         * synchronized 有对象锁和实例锁
         * 对象锁 : 静态方法上 和 锁类
         * 实例锁 : 普通方法上 和 锁实例
         * 类只有一个, 实例对象会有多个  this代表类实例
         *
         * 锁升级 ? 为了提高性能 jdk 1.6之后引入的
         * 无锁  没有锁   锁第一次被进入时,没有锁
         * 升级为 偏向锁  一个线程访问锁之后, 这个锁优先给这个线程持有  对象头里会记录线程信息
         * 升级为 轻量级锁  自旋 乐观锁 CAS
         *    ---- 上面三个阶段都不会使用 系统层面的 互斥量（Mutex） 所以比较节省资源
         * 升级为 重量级互斥锁
         */
    }
}
