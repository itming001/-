package com.li.lock;


import java.util.concurrent.atomic.AtomicReference;

/**
 * @author itming
 * 自旋锁：是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，
 * 这样的好处是减少线程的上下文切换，缺点是循环会消耗CUP
 */
public class SpinLockDemo {
    /**
     *创建一个原子的线程引用
     * 原子的引用类型为null
     */
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 创建一个加锁的方法
     */
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println("线程进入。。。。");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    /**
     * 创建一个解锁的方法
     */
    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+"\t invoked myUnlock()");
    }
    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.myLock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnlock();
        },"AA").start();

        Thread.sleep(1000);

        new Thread(()->{
            spinLockDemo.myLock();
            spinLockDemo.myUnlock();
        },"bb").start();
    }
}
