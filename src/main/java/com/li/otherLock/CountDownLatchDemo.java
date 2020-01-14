package com.li.otherLock;

import com.li.enums.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
 * 倒计时锁
 * 让一些线程阻塞直到另外一些线程完成一系列操作后才被唤醒
 * countDownLatch主要有两个方法，当一个或多个线程调用await方法时，调整线程会被阻塞。
 * 其他线程调用countDown方法会将计数器减1（调用countDown方法的线程不会阻塞），
 * 当计数器的值变为零时，因调用await方法被阻塞的线程会被唤醒，继续执行
 * @author itming
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"上完自习离开教室");
                //将countdownlatch进行减1
                countDownLatch.countDown();
            }, CountryEnum.getCountry(i).getRetMessage()).start();
        }
        //将countDownLatch进行阻塞
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"班长离开教室");
    }

    public static void testCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"上完自习离开教室");
                //将countdownlatch进行减1
                countDownLatch.countDown();
            }).start();
        }
        //将countDownLatch进行阻塞
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"班长离开教室");

    }

}
