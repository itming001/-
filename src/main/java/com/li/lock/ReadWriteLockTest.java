package com.li.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author itming
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
 * 但是
 * 如果有一个线程想去写共享资源，就不应该再有其它线程可以对该资源进行读或者写
 * 小总结：
 *    读-读能共存
 *    读-写不能共存
 *    写-写不能共存
 *    写操作： 原子+独占，整个过程必须是一个完整的统一体，中间不许被分割，被打断
 */
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    /**
     * 写入操作
     * @param key
     * @param value
     */
    public void put(String key ,Object value){
        rwLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName()+"正在写入。。。。");
        map.put(key,value);
        System.out.println(Thread.currentThread().getName()+"写入完成。。。");
        rwLock.writeLock().unlock();
    }

    /**
     * 读入操作
     * @param key
     */
    public void get(String key){
        rwLock.readLock().lock();
        System.out.println(Thread.currentThread().getName()+"正在读取。。。。");
        map.get(key);
        System.out.println(Thread.currentThread().getName()+"读取完成。。。");
        rwLock.readLock().unlock();
    }
}
public class ReadWriteLockTest {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {
            final int ii = i;
            new Thread(()->{
                myCache.put(ii+"",ii+"");
            },ii+"").start();
        }

        for (int i = 0; i < 5; i++) {
            final int ii = i;
            new Thread(()->{
                myCache.get(ii+"");
            },ii+"").start();
        }
    }
}
