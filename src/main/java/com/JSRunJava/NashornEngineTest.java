package com.JSRunJava;

import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileReader;

public class NashornEngineTest {
    public static void main(String[] args) throws ScriptException {

        // ScriptEngine engine= new ScriptEngineManager().getEngineByName("JavaScript");
        //引擎名称传入JavaScript、js、javascript、nashorn、Nashorn 均可等价
        //最好指定具体的引擎名称为nashorn。若指定为JavaScript 也是采用JDK8中默认js引擎nashorn
        //  ScriptEngine engine= new ScriptEngineManager().getEngineByName("nashorn");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("Nashorn");

        // jjs a.js
        // jjs a.js -- aa bb cc
        // jjs  [回车进入交换命令模式]
      /* Integer  result = (Integer) engine.eval("10+3");
       System.out.println(result);*/


        String js = "var a=10;var b=20; var c=a+b;c;";
        Double o = (Double) engine.eval(js);
        System.out.println(o);
    }


    /**
     * java中调用js脚本文件中的函数
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        String path = this.getClass().getResource("script.js").getPath();//获取文件路径
        engine.eval(new FileReader(new File(path)));//执行文件
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("func1", "张三");//调用js中函数
        System.out.println("返回结果: " + result);

    }
}
