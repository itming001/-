package com.li.recursion;

/**
 * 递归测试
 * 对应力扣 22. 括号生成
 * @author dell
 */
public class Test {
    public static void main(String[] args) {
        generate(0,6,"");
    }

    public static void generate(int level ,int max ,String s){
        if(level >= max){
            System.out.println(s);
            return;
        }
        generate(level+1,max,s+"(");
        generate(level+1,max,s+")");
    }
}
