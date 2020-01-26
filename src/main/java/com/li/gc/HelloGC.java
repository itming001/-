package com.li.gc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author itming
 */
public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("*******************");
//        byte[] bytes = new byte[50 * 1024 * 1024];
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<Integer> collect = list.stream().filter(x -> x == 1).collect(Collectors.toList());


    }
}
