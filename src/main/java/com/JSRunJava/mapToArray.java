package com.JSRunJava;

import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

public class mapToArray {
    @Test
    public void test(){
        String syntax = "arg=['@1.stock_name','@1.instrument_id','@1.list_date']$function format(args){\r\n" +
                "\treturn args[0][0]+'('+args[1][0]+')的上市日期为'+args[2][0];\r\n" +
                "}\n";
        String[][] params = new String[][]{{"中国平安","中国平安"},{"601318.EQSH","02318.EQHK2"},{"2007-03-01","2004-06-242"}};
        //String[] args = new String[]{"中国平安","601318","2007-03-01"};
        List<List> args = new ArrayList<>();
        List<Object> arg01 = new ArrayList<>();
        List<Object> arg02 = new ArrayList<>();
        List<Object> arg03 = new ArrayList<>();

        arg01.add(0,"中国平安");
        arg01.add(1,"中国平安");
        arg02.add(0,"601318.EQSH");
        arg02.add(1,"02318.EQHK2");
        arg03.add(0,"2007-03-01");
        arg03.add(1,"2004-06-242");
        args.add(arg01);
        args.add(arg02);
        args.add(arg03);
        System.out.println("args:"+args);
        for (int i = 0; i < params.length; i++) {
            for (int j = 0; j < params[i].length; j++) {
                System.out.println("params["+i+"]["+j+"]:"+params[i][j]);
            }
            System.out.println("=========");
        }

        String functionFormat = syntax.split("\\$")[1];
        System.out.println(functionFormat);

        Object format = null;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            engine.eval(functionFormat);
            if (engine instanceof Invocable) {
                Invocable inv = (Invocable) engine;
                format = inv.invokeFunction("format",args);
            }
        } catch (
                ScriptException e) {
            e.printStackTrace();
        } catch (
                NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("格式化结果：" + format);
    }

}
