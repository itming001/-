package com.li.stack;

import java.util.Stack;

/**
 * 对应力扣84题
 *
 * @author dell
 */
public class Test2 {
    public static void main(String[] args) {
        int[] maxArea = new int[]{2, 1, 5, 6, 2, 3};
//        System.out.println(Test2.largestRectangleArea(maxArea));
        System.out.println(Test2.largestRectangleStackArea(maxArea));

    }

    /**
     * 暴力求解
     *
     * @param heights
     * @return
     */
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

    /**
     * 使用栈进行求解
     *
     * @param heights
     * @return
     */
    public static int largestRectangleStackArea(int[] heights) {
        Stack<Integer> ints = new Stack<Integer>();
        ints.push(-1);
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            while (ints.peek() != -1 && heights[ints.peek()] > heights[i]) {
                //如果栈顶元素大于即将入栈的元素，栈中的元素进行出栈操作
                Integer peek = ints.peek();
                int nextArea = (i - peek) * heights[ints.pop()];
                maxArea = Math.max(maxArea, nextArea);
            }
            ints.push(i);
        }
        while (ints.peek() != -1) {
            Integer pop = ints.pop();
            int next = (heights.length - ints.peek() - 1) * heights[pop];
            System.out.println(next);
            maxArea = Math.max(maxArea,next);
        }
        return maxArea;
    }
}
