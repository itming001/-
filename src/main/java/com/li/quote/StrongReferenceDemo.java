package com.li.quote;

import java.util.WeakHashMap;

/**
 * @author itming
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object object = new Object();
        Object object2 = object;
        object = null;
        System.gc();
        System.out.println(object2);



    }
}
