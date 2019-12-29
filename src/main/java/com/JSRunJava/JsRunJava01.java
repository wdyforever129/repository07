package com.JSRunJava;

import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsRunJava01 {

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

        HashMap<String, Object> map03 = new HashMap<String, Object>();
        map03.put("stock_name", "中国平安3");
        map03.put("instrument_id", "02318.EQHK3");
        map03.put("list_date", "2004-06-2433");

        list.add(map01);
        list.add(map02);
        list02.add(map03);
        tableAndResultMap.put("@1", list);
        tableAndResultMap.put("@1", list);
        tableAndResultMap.put("@2", list02);
        System.out.println(tableAndResultMap);
        ArrayList<String> params = new ArrayList<>();
        params.add("name='@1.stock_name'");
        params.add("id='@1.instrument_id'");
        params.add("attr_value='@2.list_date'");

        Integer size = 1;

        String syntax = "function format(list){\n" +
                "\treturn list.get(0)+'('+list.get(1)+')的上市日期为'+list.get(2);\n" +
                "}";
        //System.out.println(syntax);

        getResult(syntax, params, size, tableAndResultMap);
    }

    public Object getResult(String syntax, List<String> params, Integer size, Map<String, List<Map<String, Object>>> tableAndResultMap) {
        List<String> tableList = new ArrayList<>();
        List<String> keyList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();

        for (String param : params) {
            Matcher matcher = Pattern.compile("'(.*?)'").matcher(param);
            while (matcher.find()) {
                String group = matcher.group(1);
                tableList.add(group.split("\\.")[0]);
                keyList.add(group.split("\\.")[1]);
            }
        }
        //从tableAndResultMap集合中查询nameList中需要的值
        //根据table找对应表@1的List结果:1张表
        /*Set<String> keySet = tableAndResultMap.keySet();
        for (String table : keySet) {
            List<Map<String, Object>> list = tableAndResultMap.get(table);
            int cont=1;
            for (Map<String, Object> map : list) {
                ArrayList<Object> list1 = new ArrayList<>(map.values());
                for (int i = 0; i < list1.size(); i++) {

                }
                System.out.println(list1+"...");
                *//*if (cont == size) {
                    for (String tableResultKey : map.keySet()) {
                        for (int i = 0; i < tableList.size(); i++) {
                            if (tableList.get(i).equals(table) && keyList.get(i).equals(tableResultKey)) {
                                valueList.add((String) map.get(tableResultKey));
                            }
                        }
                    }
                }*//*
                cont++;
            }
        }*/

        //数组嵌套数组转换为集合嵌套集合
        /*ArrayList<String> list = null;
        ArrayList<ArrayList<String>> list2 = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < str.length; i++) {
            list = new ArrayList<String>();
            for (int j = 0; j < str[i].length; j++) {
                list.add(str[i][j]);
            }
            list2.add(list);
        }
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }*/

        System.out.println(tableList + "\n" + keyList + "\n" + valueList);

        Matcher syntaxMatcher = Pattern.compile("\\(.*?\\)").matcher(syntax);
        syntaxMatcher.find();
        String syntaxGroup = syntaxMatcher.group();
        Matcher matcher = Pattern.compile("'(.*?)'").matcher(syntaxGroup);
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println("group" + group);
        }
        System.out.println(syntax);
        Object format = null;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            engine.eval(syntax);
            if (engine instanceof Invocable) {
                Invocable inv = (Invocable) engine;
                format = inv.invokeFunction("format", valueList);
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("format:" + format);
        return format;
    }

    @Test
    public void test() {
        ArrayList<String> list = new ArrayList<>();
        list.add("中国平安");
        list.add("601318.EQSH");
        list.add("2007-03-01");

        String name = "中国平安";
        String id = "601318.EQSH";
        String attr_value = "2007-03-01";
        String syntax = "function format(name,id,attr_value){\n" +
                "\treturn name+'('+id+')的上市日期为'+attr_value;\n" +
                "}";

        Object format = null;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            engine.eval(syntax);
            if (engine instanceof Invocable) {
                Invocable inv = (Invocable) engine;
                if (name != null) {
                    format = inv.invokeFunction("format", name);
                }
                if (name != null && id != null) {
                    format = inv.invokeFunction("format", name, id);
                }
                if (attr_value != null) {
                    format = inv.invokeFunction("format", name, id, attr_value);
                }
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
