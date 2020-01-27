package com.li.quote;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 弱引用整合引用队列的使用
 * @author itming
 * java提供了4中引用类型，在垃圾回收的时候，都有各自的特点
 * ReferenceQueue是用来配合引用工作的，没有ReferenceQueue一样可以运行
 *
 * 创建引用的时候可以指定关联的队列，当GC释放对象内存的时候，会将引用加入到引用队列
 * 如果创建程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动
 * 这相当于一种会回收机制
 *
 * 当关联的引用队列中有数据 的时候，意味着引用指向的堆内存中的对象被回收。通过这种方式，JVM允许我们在对象被销毁之后，
 * 做一些我们自己想做的事情。
 */
public class ReferenceQueueDemo {
    public static void main(String[] args) {
        Object object = new Object();
        //创建一个引用队列
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        //创建一个弱引用
        WeakReference<Object> weakReference = new WeakReference<>(object,referenceQueue);
        System.out.println(object);
        //获取虚引用对象
        System.out.println(weakReference.get());
        //获取队列中的数据
        System.out.println(referenceQueue.poll());
        //将object置为空
        object = null;
        //模拟进行gc进行垃圾回收
        System.gc();
        System.out.println("==================");
        System.out.println(object);
        //获取虚引用对象
        System.out.println(weakReference.get());
        //获取队列中的数据
        System.out.println(referenceQueue.poll());
    }




}
