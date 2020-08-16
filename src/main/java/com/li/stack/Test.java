package com.li.stack;

import java.util.Stack;

/**
 * 判断括号是否有效
 * @author dell
 */
public class Test {
    public static void main(String[] args) {
        //判断字符串是否有效
        String str = "";
        boolean valid = Test.isValid(str);
        if(valid){
            System.out.println("字符串有效");
        }else {
            System.out.println("字符串无效");
        }
    }
    public static boolean isValid(String str){
        Stack<Character> characters = new Stack<>();
        for(char s : str.toCharArray()){
            if('{' == s){
                characters.push('}');
            } else if('(' == s){
                characters.push(')');
            }else if('[' == s){
                characters.push(']');
            }else {
                if(characters.isEmpty() || s != characters.pop()){
                   return false;
                }
            }

        }
        return characters.isEmpty();
    }
}
