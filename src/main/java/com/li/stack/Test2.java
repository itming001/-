package com.li.stack;

/**
 * 对应力扣84题
 *
 * @author dell
 */
public class Test2 {
    public static void main(String[] args) {
        int[] maxArea = new int[]{2, 1, 5, 6, 2, 3};
        System.out.println(Test2.largestRectangleArea(maxArea));
    }

    public static int largestRectangleArea(int[] heights) {
        int area = 0;
        for (int i = 0; i < heights.length; i++) {


            int left = i;
            //找到最左边为比当前值最小的值
            while (left > 0 && heights[left] >= heights[i]) {
                left--;
            }
            //找到最右边比当前值最小的值
            int right = i;
            while (right < heights.length && heights[right] >= heights[i]) {
                right++;
            }

            area = Math.max(area, (right - left) * heights[i]);

        }
        return area;
    }
}
