package com.li.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Groovy动态脚本测试
 * @author lym
 */
public class Test extends Script {

    public static void main(String[] args) {
        //groovy执行对象赋值语句
        String scriptContent = "student.address=\"河北省衡水市1\";\n" +
        "student.age=12;\n" +
        "student.name=\"王五\";\n" +
        "student;";
        System.out.println(Test.assignment(scriptContent));

        //groovy执行过滤语句  执行的语句必须一条一条执行
        String scriptContentFilter = "student.age>12;";
        System.out.println(Test.filter(scriptContentFilter));

    }

    /**
     * 初始化groovy脚本相关信息
     * @return
     */
    public static GroovyShell init(Binding groovyBinding){
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(Test.class.getClassLoader());
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("utf-8");
        compilerConfiguration.setScriptBaseClass(Test.class.getName());
        GroovyShell groovyShell = new GroovyShell(groovyClassLoader, groovyBinding, compilerConfiguration);
        return groovyShell;
    }
    /**
     * Groovy脚本的赋值操作
     * scriptContent 需执行的赋值语句
     */
    public static Object assignment(String scriptContent){
       //初始化一个对象操作
        HashMap<String, Object> student = new HashMap<>();
        student.put("age",null);
        student.put("name",null);
        student.put("address",null);
        //初始化Binding
        Binding groovyBinding = new Binding();
        //绑定GroovyBinding赋值信息
        groovyBinding.setVariable("student",student);
        //获取Groovyshell对象
        GroovyShell groovyShell = Test.init(groovyBinding);
        //将执行语句进行绑定
        Script script = groovyShell.parse(scriptContent);
        //运行代码
        Object obj = script.run();
        return obj;
    }

    /**
     * Groovy过滤操作
     */
    public static Object filter(String scriptContent){
        //初始化一个对象操作
        HashMap<String, Object> student = new HashMap<>();
        student.put("age",89);
        student.put("name","张三");
        student.put("address","河北省衡水市");
        //初始化Binding
        Binding groovyBinding = new Binding();
        //绑定GroovyBinding赋值信息
        groovyBinding.setVariable("student",student);
        //获取Groovyshell对象
        GroovyShell groovyShell = Test.init(groovyBinding);
        //将执行语句进行绑定
        Script script = groovyShell.parse(scriptContent);
        //运行代码
        Object obj = script.run();
        return obj;
    }

    @Override
    public Object run() {
        return null;
    }
}

