package com.li.array;

import java.util.Arrays;

/**
 * 实现数组之间的拷贝
 * @author dell
 */
public class ArrayCopy {
    /**
     * 实现数组之间的拷贝
     * @param arr 数组
     * @param left 左边界
     * @param mid  中间界
     * @param right 右边界
     */
    public static void merage(int[] arr, int left, int mid, int right) {
        //中间数组
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        for (int p = 0; p < temp.length; p++) {
            arr[left + p] = temp[p];
        }
        System.out.println(Arrays.toString(arr));
    }
}
