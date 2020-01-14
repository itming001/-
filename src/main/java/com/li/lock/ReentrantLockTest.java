package com.li.lock;


/**
 *
 * @author itming
 */

import java.util.concurrent.locks.ReentrantLock;

/**
 *创建资源类
 */
class Phone implements Runnable{
    /**
     * 发送短信
     * @throws Exception
     */
    public synchronized void sendSMS () throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t invoked sendSMS()");
        sendEmail ();
    }

    /**
     * 发送邮件
     * @throws Exception
     */
    public synchronized void sendEmail () throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t invoked sendEmail()");
    }
    ReentrantLock reentrantLock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }
    public void get(){
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getId()+"\t invoked get()");
            set();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }

    public void set(){
        try {
            System.out.println(Thread.currentThread().getId()+"\t invoked set()");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }

}

/**
 *可重入锁  递归锁
 * 指在同一线程外层函数获得锁之后，内层递归函数仍然能获得该锁的代码
 * 指同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 * 也既是说，线程可以进入任何一个它已经拥有锁的代码块
 * @author itming
 */
public class ReentrantLockTest {
    public static void main(String[] args) throws Exception {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"AA").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"BB").start();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("=========================测试reentrantLock是重入锁");
        Thread thread3 = new Thread(phone);
        Thread thread4 = new Thread(phone);
        thread3.start();
        thread4.start();
    }
}
