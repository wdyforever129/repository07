package com.JSRunJava;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

public class JsRunJava03 {

    public List test(String syntax, List format) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            engine.eval(syntax);
            if (engine instanceof Invocable) {
                Invocable inv = (Invocable) engine;
                //format = inv.invokeFunction("format", param01);
            }
        } catch (
                ScriptException e) {
            e.printStackTrace();
        } /*catch (
                NoSuchMethodException e) {
            e.printStackTrace();
        }*/
        System.out.println("格式化结果：" + format);
        return format;
    }
}
