package com.dataFormat;

import com.util.RegexUtil;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DataFormate {
    //map<String,list<map<string，string>>>转map<string，map<string，list<string>>>
    @Test
    public void test() {
        Map<String, List<Map<String, Object>>> tableAndResultMap = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("indicname11", "GDP:现价:当即值01");
        map1.put("indicname12", "GDP:现价:当即值02");
        map1.put("indicname13", "GDP:现价:当即值03");
        Object indicname14 = map1.get("indicname14");
        System.out.println("ind:" + indicname14);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("indicname11", "GDP:现价:当即值21");
        map2.put("indicname12", "GDP:现价:当即值22");
        map2.put("indicname13", "GDP:现价:当即值23");
        ArrayList<Map<String, Object>> tableList1 = new ArrayList<>();
        tableList1.add(map1);
        tableList1.add(map2);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("PMI31", "GDP:现价:当即值1");
        map3.put("PMI32", "GDP:现价:当即值2");
        map3.put("PMI33", "GDP:现价:当即值3");
        map3.put("PMI34", "GDP:现价:当即值4");
        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("PMI31", "GDP:现价:22当即值");
        map4.put("PMI32", "GDP:现价:66当即值");
        map4.put("PMI33", "GDP:现价:77当即值");
        map4.put("PMI34", "GDP:现价:88当即值");
        ArrayList<Map<String, Object>> tableList2 = new ArrayList<>();
        tableList2.add(map3);
        tableList2.add(map4);
        tableAndResultMap.put("@1", tableList1);
        tableAndResultMap.put("@2", tableList2);
        System.out.println(tableAndResultMap);
        //Map<String, List<Map<String,Object>>>
        Map<String, Map<String, List>> resultMap = new HashMap<>();
        List<Object> list = new ArrayList<>();
        List<Object> tempList = null;
        //表名
        for (String tableName : tableAndResultMap.keySet()) {
            HashMap<String, List> temp = new HashMap<>();
            HashMap<String, List> map = new HashMap<>();
            //表内容
            List<Map<String, Object>> tableContent = tableAndResultMap.get(tableName);
            //每行内容
            Map<String, Object> map01 = tableContent.get(0);
            for (String key01 : map01.keySet()) {
                tempList = new ArrayList<>();
                for (int j = 0; j < tableContent.size(); j++) {
                    Map<String, Object> map02 = tableContent.get(j);
                    for (String key02 : map02.keySet()) {
                        if (key01 == key02) {
                            tempList.add(map02.get(key02));
                        }
                    }
                }
                list.addAll(tempList);
                map.put(key01, list);
                list = new ArrayList<Object>();
            }

            temp.putAll(map);
            //System.out.println(temp);
            resultMap.put(tableName, temp);
            System.out.println(resultMap);
        }
        System.out.println("=================");
        /*for (Object key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
            System.out.println("=================");
        }*/
        System.out.println(resultMap);
    }

    @Test
    public void test02() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("key1", 1);

        Map<String, Integer> mapFirst = new HashMap<String, Integer>();
        mapFirst.putAll(map); //深拷贝

        System.out.println(mapFirst);

        map.put("key2", 2);

        System.out.println(mapFirst);
    }

    @Test
    public void test001() {
        ArrayList<Object> distinct = new ArrayList<>();
        distinct.add("in_id");
        distinct.add("in_company");

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("in_id", "000011.SHQH");
        map1.put("in_date", "2019-10-11");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("in_id", "000011.SHQH");
        map2.put("in_date", "2019-12-12");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("in_id", "000012.SHQH");
        map3.put("in_date", "2019-11-15");
        Map<String, Object> map4 = new HashMap<>();
        map4.put("in_id", "000015.SHQH");
        map4.put("in_date", "2019-10-17");
        Map<String, Object> map5 = new HashMap<>();
        map5.put("in_id", "000015.SHQH");
        map5.put("in_date", "2019-12-16");
        Map<String, Object> map6 = new HashMap<>();
        map6.put("in_company", "平安");
        map6.put("in_date", "2019-10-17");
        Map<String, Object> map7 = new HashMap<>();
        map7.put("in_company", "平安");
        map7.put("in_date", "2019-12-16");
        list.add(map6);
        list.add(map7);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        System.out.println("ind:" + list);

        for (int i = 0; i < distinct.size(); i++) {
            String distince = (String) distinct.get(i);
            for (int j = 0; j < list.size() - 1; j++) {
                Object o = list.get(j).get(distince);
                if (o != null) {
                    for (int k = j + 1; k < list.size(); k++) {
                        Object o1 = list.get(k).get(distince);
                        if (o == o1) {
                            list.remove(k--);
                        }
                    }
                }
            }
        }
        System.out.println(list);
    }

    @Test
    public void test002() {
    }

    public static List<Map<String, Object>> merge(List<Map<String, Object>> m1, List<Map<String, Object>> m2) {

        m1.addAll(m2);

        Set<String> set = new HashSet<>();

        return m1.stream()
                .collect(Collectors.groupingBy(o -> {
                    //暂存所有key
                    set.addAll(o.keySet());
                    //按a_id分组
                    return o.get("a_id");
                })).entrySet().stream().map(o -> {
                    //合并
                    Map<String, Object> map = o.getValue().stream().flatMap(m -> {
                        return m.entrySet().stream();
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b));
                    //为没有的key赋值0
                    set.stream().forEach(k -> {
                        if (!map.containsKey(k)) map.put(k, 0);
                    });

                    return map;
                }).collect(Collectors.toList());

    }

    @Test
    public void test003() {
        List<Map<String, Object>> list01 = new ArrayList<>();
        List<Map<String, Object>> list02 = new ArrayList<>();
        List<Map<String, Object>> list03 = new ArrayList<>();
        List<Map<String, Object>> list04 = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("in_id", "000011.SHQH");
        map1.put("in_date", "2019-10-11");
        map1.put("xueke", "化学");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("in_id", "000012.SHQH");
        map2.put("in_date", "2019-12-12");
        map2.put("xueke", "物理");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("in_id", "000013.SHQH");
        map3.put("in_date", "2019-11-15");
        map3.put("xueke", "生物");
        Map<String, Object> map4 = new HashMap<>();
        map4.put("in_id", "000011.SHQH");
        map4.put("in_company", "tianhe");
        map4.put("zichang", "1000");
        Map<String, Object> map5 = new HashMap<>();
        map5.put("in_id", "000012.SHQH");
        map5.put("in_company", "java");
        map5.put("in_date", "2019-12-16");
        Map<String, Object> map6 = new HashMap<>();
        map6.put("in_id", "000013.SHQH");
        map6.put("in_company", "太平");
        map6.put("zichang", "30000");
        Map<String, Object> map7 = new HashMap<>();
        map7.put("in_company", "中华");
        map7.put("zichang", "50000");
        Map<String, Object> map9 = new HashMap<>();
        map9.put("in_company", "华夏");
        map9.put("zichang", "50000");
        Map<String, Object> map8 = new HashMap<>();
        map8.put("in_date", "2019-03-21");
        map8.put("in_id", "000022.SHQH");
        list01.add(map1);
        list01.add(map2);
        list01.add(map3);
        list02.add(map4);
        list02.add(map5);
        list02.add(map6);
        list04.add(map7);
        list04.add(map8);
        list04.add(map9);
        System.out.println(list01);
        System.out.println(list02);
        //System.out.println(list03);
        // System.out.println(list04);
        List<Map<String, Object>> list = merge01(list01, list02);
    }

    public List<Map<String, Object>> merge01(List<Map<String, Object>> m1, List<Map<String, Object>> m2) {
        String mergeStr = "";
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < m1.size(); i++) {
            map.putAll(m1.get(i));
            Set<Map.Entry<String, Object>> entries1 = m1.get(i).entrySet();
            loop:
            for (Map.Entry<String, Object> entry1 : entries1) {
                if (mergeStr.equals(entry1.getKey())) {
                    for (int j = 0; j < m2.size(); j++) {
                        Set<Map.Entry<String, Object>> entries2 = m2.get(j).entrySet();
                        for (Map.Entry<String, Object> entry2 : entries2) {
                            if (entry1.equals(entry2)) {
                                map.putAll(m2.get(j));
                                //temp.putAll(map);
                                list.add(map);
                                map = new HashMap<>();
                                break loop;
                            }
                        }
                    }
                }
            }

        }
        System.out.println("m:" + list);
        return list;
    }

    public void test01() {
        List<Map<String, Object>> list01 = new ArrayList<>();
        List<Map<String, Object>> list02 = new ArrayList<>();
        // 合并两个List<Map<String,Object>>
        // 1、获取list01里面的map01
        for (int i = 0; i < list01.size(); i++) {
            Map<String, Object> map01 = list01.get(i);
            // 2、获取map01里面的key01
            Set<String> keySet01 = map01.keySet();
            // 3、获取list02里面的map02
            for (int j = 0; j < list02.size(); j++) {
                Map<String, Object> map02 = list02.get(j);
                // 4、获取map02里面的key02
                Set<String> keySet02 = map02.keySet();
                // 5、比较map01.value.equals(map02.value)

            }
        }
        // 6、ture 合并 map02到map01

    }

    @Test
    public void test181() {
        int cont = 1;
        String task_con = "(<@2.instrument_id/> = <@1.instrument_id/>)";
        HashMap<String, List<Map>> tableAndresultMap = new HashMap<>();
        ArrayList<Map> mapList = new ArrayList<>();
        HashMap<Object, Object> map01 = new HashMap<>();
        HashMap<Object, Object> map02 = new HashMap<>();
        map01.put("indecname", "GDP:现价...");
        map01.put("endtate", "201812");
        mapList.add(map01);
        tableAndresultMap.put("@1", mapList);
        map02.put("indecname", "GDP:现价...");
        map02.put("endtate", "201812");
        mapList.add(map02);
        tableAndresultMap.put("@2", mapList);

        task_con.substring(task_con.indexOf("(") + 1, task_con.indexOf(")"));
        String[] split = task_con.split(" = ");
        String tableName01 = split[0].substring(task_con.indexOf("@"), task_con.indexOf("."));
        String tableName02 = split[1].substring(task_con.indexOf("@"), task_con.indexOf("."));
        List<Map> result = getConcatRow(tableAndresultMap.get(tableName01), tableAndresultMap.get(tableName02));
        getResult(tableAndresultMap, result, cont++);
    }

    private List<Map> getConcatRow(List<Map> mapList01, List<Map> mapList02) {
        ArrayList<Map> concatList = new ArrayList<>();
        if (mapList01 != null && !mapList01.isEmpty()) {
            for (Map map01 : mapList01) {
                concatList.add(map01);
            }
        }
        if (mapList02 != null && !mapList02.isEmpty()) {
            for (Map map02 : mapList02) {
                concatList.add(map02);
            }
        }
        System.out.println(concatList);
        return concatList;
    }

    private void getResult(Map tableAndresultMap, List result, int cont) {
        tableAndresultMap.put("@" + cont, result);
    }

    @Test
    public void test182() {
        RegexUtil regexUtil = new RegexUtil();

        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> transitionMap = new HashMap<>();
        transitionMap.put("&", "AND");
        transitionMap.put("|", "OR");

        String taskCon = "(stock_name like ['保利地产','万科']) & (end_date is null) & (stype = 'EQA') & (valid_to_dt = '9999-12-31')";
        String regex = "(?: *& *)?\\((stock_name|instrument_name) ([^)]*?) ([^)]*?)\\)";
        //获取(stock_name like ['保利地产','万科'])
        List taskList = regexUtil.getRegexList(taskCon, regex);
        String task = (String) taskList.get(0);

        //'保利地产','万科'
        String regex02 = "('(.*?)')";
        List stockList = regexUtil.getRegexList(task, regex02);
        String key = (String) stockList.get(0);

        //['保利地产','万科']
        String regex03 = "(\\[(.*?)])";
        List val = regexUtil.getRegexList(task, regex03);

        ArrayList<String> list = new ArrayList<>();

        //(stock_name like ['保利地产'])和(stock_name like ['万科'])
        for (int i = 0; i < stockList.size(); i++) {
            String replace = task.replace((String) val.get(0), (String) stockList.get(i));
            list.add(taskCon.replace(task, replace));
        }
        System.out.println(list);
    }

    @Test
    public void test191() {
        String taskCon = "(stock_name like '保利地产') & (end_date is null) &(stype = 'EQA') & (valid_to_dt = '9999-12-31')";
        HashMap<String, Map> consMap = new HashMap<>();
        HashMap<Object, Object> itemsMap = new HashMap<>();
        HashMap<Object, Object> oprVal = new HashMap<>();
        RegexUtil regexUtil = new RegexUtil();

        String splitString = "\\) & \\(";
        if (taskCon.contains(splitString)) {
            String[] consArr = taskCon.split(splitString);
            for (int i = 0; i < consArr.length; i++) {
                String con = consArr[i];
                if (con.contains(" is ")) {
                    String conName = regexUtil.getRegex(con, "(?=\"is\")");//字符之前
                    String val = regexUtil.getRegex(con, "(?<=\"is\")");//字符之后
                    oprVal.put("opr", " is ");
                    oprVal.put("val", val);
                    itemsMap.put(conName, oprVal);
                }
                if (consArr[i].contains(" = ")) {
                    String conName = regexUtil.getRegex(con, "(?=\"=\")");//字符之前
                    String val = regexUtil.getRegex(con, "(?<=\"=\")");//字符之后
                    oprVal.put("opr", " = ");
                    oprVal.put("val", val);
                    itemsMap.put(conName, oprVal);
                }
                if (consArr[i].contains("like")) {
                    StringBuffer buffer = new StringBuffer();
                    String conName = regexUtil.getRegex(con, "(?=\"like\")");//字符之前
                    String val = regexUtil.getRegex(con, "(?<=\"like\")");//字符之后
                    val = buffer.append("'%").append(val).append("%'").toString();
                    oprVal.put("opr", " like ");
                    oprVal.put("val", val);
                    itemsMap.put(conName, oprVal);
                }
                consMap.put(" AND ", itemsMap);
            }
        }
        System.out.println(consMap);
    }
}
