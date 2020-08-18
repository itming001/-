package com.li.queue;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.Arrays;

/**
 * 测试使用双端队列解决排序问题
 * 对应力扣239
 *
 * @author liyanming
 */
public class Test {
    public static void main(String[] args) {
        int[] array = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int[] queueMax = Test.getQueueMax(array,3);
        //正确信息 3,3,5,5,6,7
        System.out.println(Arrays.toString(queueMax));
    }

    /**
     * 使用队列解决求最大值
     * 暴力求解
     * @param max
     * @return
     */
    public static int[] getQueueMax(int[] max,int k) {
        int[] newMax = new int[8];
        int maxValue = Integer.MIN_VALUE ;
        for (int i = 0; i < max.length-k+1; i++) {
            for (int j = i; j < k+i; j++) {
                maxValue = Math.max(maxValue, max[j]);
            }
            newMax[i] = maxValue;
        }
        return newMax;
    }


}
