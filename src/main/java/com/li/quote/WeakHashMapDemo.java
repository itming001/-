package com.li.quote;

import java.util.WeakHashMap;

/**
 * @author itming
 * weakHashMap 只要将key的引用置为空 key-value将会被垃圾进行回收
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        weakHashMap();
    }

    private static void weakHashMap() {
        //创建一个waekHashMap
        WeakHashMap<Integer,String> weakHashMap = new WeakHashMap<>();
        Integer key = new Integer(1);
        String val = "WeakHashMap";
        weakHashMap.put(key,val);
        System.out.println(weakHashMap);
        //将key置为空
        key = null;
        System.out.println(weakHashMap);
        //进行垃圾回收
        System.gc();
        System.out.println(weakHashMap);

    }
}
