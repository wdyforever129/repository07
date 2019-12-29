package com.JSRunJava;

import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsRunJava {

    @Test
    public void JsRunJava01() {
        Map<String, List<Map<String, Object>>> tableAndResultMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> list02 = new ArrayList<>();
        HashMap<String, Object> map01 = new HashMap<String, Object>();
        map01.put("stock_name", "中国平安");
        map01.put("instrument_id", "601318.EQSH");
        map01.put("list_date", "2007-03-01");

        HashMap<String, Object> map02 = new HashMap<String, Object>();
        map02.put("stock_name", "中国平安2");
        map02.put("instrument_id", "02318.EQHK2");
        map02.put("list_date", "2004-06-242");

        list.add(map01);
        list.add(map02);
        tableAndResultMap.put("@1", list);
        tableAndResultMap.put("@1", list);
        System.out.println(tableAndResultMap);

        ArrayList<String> params = new ArrayList<>();
        params.add("name='@1.stock_name'");
        params.add("id='@1.instrument_id'");
        params.add("attr_value='@1.list_date'");

        Integer size = 1;

       /* String syntax = "function format(list){\n" +
                "name = list.get(0);\n"+
                "id = list.get(1);\n"+
                "attr_value = list.get(2);\n"+
                "\treturn name+'('+id+')的上市日期为'+attr_value;\n" +
                "}";*/
        /*String syntax = "arg=['@1.stock_name','@1.instrument_id','@1.list_date'];function format(arg){\n" +
                "\treturn arg[0][0]+'('+arg[1][0]+')的上市日期为'+arg[2][0];\n" +
                "}";*/
        String syntax = "arg=['@1.stock_name','@1.instrument_id','@1.list_date'];\n" +
                "function format(arg){\n" +
                "\tfor(var i=0;i<arg.length;i++){\n" +
                "\t\tfor(var j=0;j<arg[i].length;j++){\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\treturn arg[0][0]+'('+arg[1][0]+')的上市日期为'+arg[2][0];\n" +
                "}";

        getResult(syntax, params, size, tableAndResultMap);
    }

    public Object getResult(String syntax, List<String> params, Integer size, Map<String, List<Map<String, Object>>> tableAndResultMap) {
        List<String> tableList = new ArrayList<>();
        List<String> keyList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();

        String arg = syntax.split(";")[0];//arg=['@1.stock_name','@1.instrument_id','@1.list_date']
        String functionFormat = syntax.split("'];")[1];//arg=['@1.stock_name','@1.instrument_id','@1.list_date']
        System.out.println("1212:"+functionFormat);
        //for (String param : params) {
        Matcher matcher = Pattern.compile("'(.*?)'").matcher(arg);
        while (matcher.find()) {
            String group = matcher.group(1);
            tableList.add(group.split("\\.")[0]);
            keyList.add(group.split("\\.")[1]);
        }
        System.out.println(tableList + "," + keyList);
        //}
        //从tableAndResultMap集合中查询nameList中需要的值
        //根据table找对应表@1的List结果:1张表
        String[][] param01 = null;
        Set<String> keySet = tableAndResultMap.keySet();
        for (String table : keySet) {
            List<Map<String, Object>> list = tableAndResultMap.get(table);
            for (Map<String, Object> map : list) {
                param01 = new String[map.size()][tableAndResultMap.get(table).size()];
            }
        }

        String[][] temp = null;
        for (String table : keySet) {
            List<Map<String, Object>> list = tableAndResultMap.get(table);
            System.out.println("111:" + list);//[{stock_name=中国平安, instrument_id=601318.EQSH, list_date=2007-03-01}, {stock_name=中国平安2, instrument_id=02318.EQHK2, list_date=2004-06-242}]
            int j = 0;
            for (Map<String, Object> map : list) {
                System.out.println("222" + map);//{stock_name=中国平安, instrument_id=601318.EQSH, list_date=2007-03-01}
                System.out.println("1212" + map.size());//3  temp[3][2]
                System.out.println("12321" + tableAndResultMap.get(table).size());//2
                temp = new String[map.size()][tableAndResultMap.get(table).size()];
                //if (cont == size) {
                for (String tableResultKey : map.keySet()) {
                    System.out.println("333" + tableResultKey);//key
                    for (int i = 0; i < tableList.size(); i++) {
                        if (tableList.get(i).equals(table) && keyList.get(i).equals(tableResultKey)) {
                            param01[i][j] = map.get(tableResultKey).toString();
                            System.out.println(param01[i][j]);
                        }
                    }
                }
                j++;
            }
        }

        System.out.println("==============");
        for (int i = 0; i < param01.length; i++) {
            for (int n = 0; n < param01[i].length; n++) {
                System.out.println(param01[i][n]);
            }
            System.out.println("\n");
        }
        System.out.println(functionFormat);
        Object format = null;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            engine.eval(syntax);
            if (engine instanceof Invocable) {
                Invocable inv = (Invocable) engine;
                format = inv.invokeFunction("format", param01);
            }
        } catch (
                ScriptException e) {
            e.printStackTrace();
        } catch (
                NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("格式化结果：" + format);
        return format;
    }


    @Test
    public void test() {
        List<String> tableList = new ArrayList<>();
        List<String> keyList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("中国平安");
        list.add("601318.EQSH");
        list.add("2007-03-01");

        String name = "中国平安";
        String id = "601318.EQSH";
        String attr_value = "2007-03-01";
        String syntax = "function format(list){\n" +
                "\treturn name+'('+id+')的上市日期为'+attr_value;\n" +
                "}";

        Matcher syntaxMatcher = Pattern.compile("\\(.*?\\)").matcher(syntax);
        syntaxMatcher.find();
        String syntaxGroup = syntaxMatcher.group();//name='@1.stock_name',id='@1.instrument_id',attr_value='@1.list_date'
        Matcher matcher = Pattern.compile("'(.*?)'").matcher(syntaxGroup);
        while (matcher.find()) {
            String group = matcher.group(1);
            tableList.add(group.split("\\.")[0]);
            keyList.add(group.split("\\.")[1]);
        }

        Object format = null;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            engine.eval(syntax);
            if (engine instanceof Invocable) {
                Invocable inv = (Invocable) engine;
                format = inv.invokeFunction("format");
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("format:" + format);
    }

    //function中截取字符串的代码
    /*Matcher syntaxMatcher = Pattern.compile("\\(.*?\\)").matcher(syntax);
    syntaxMatcher.find();
    String syntaxGroup = syntaxMatcher.group();//name='@1.stock_name',id='@1.instrument_id',attr_value='@1.list_date'
    Matcher matcher = Pattern.compile("'(.*?)'").matcher(syntaxGroup);
    while (matcher.find()) {
    String group = matcher.group(1);
    tableList.add(group.split("\\.")[0]);
    keyList.add(group.split("\\.")[1]);
    }*/
}
