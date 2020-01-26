package com.li.gc;

/**
 * GC ROOT demo
 * @author itming
 * GC ROOT:
 *  1. 虚拟机栈（栈帧中的局部变量，也叫局部变量）中引用的对象
 *  2. 方法区中的类静态属性的引用的对象
 *  3. 方法区中常量引用的对象
 *  4. 本地方法栈中JNI（Native）引用的对象
 */
public class GCROOTdemo {
    private byte[] byteArray = new byte[100 * 1024 * 1024];
    public static void m1(){
        //创建一个实例对象
        GCROOTdemo gcrooTdemo = new GCROOTdemo();
        //进行手动调用GC 进行回收
        System.gc();
        System.out.println("第一次进行GC垃圾回收");
    }
    public static void main(String[] args) {
      m1();
    }
}
