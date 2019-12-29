package com.dataFormat;

import jdk.internal.org.objectweb.asm.Handle;
import org.junit.Test;

import java.util.*;

public class ListMapMerge {
    /**
     * list<Map<String,Object>>集合的合并
     */
    @Test
    public void test01() {
        List<Map<String, Object>> list01 = new ArrayList<>();
        List<Map<String, Object>> list02 = new ArrayList<>();
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
        System.out.println("list01" + list01);
        System.out.println("list02" + list02);

        list01 = getMerge(list01, list02);
        System.out.println("111 "+list01);
    }

    /*private List<Map<String, Object>> getMerge(List<Map<String, Object>> list01, List<Map<String, Object>> list02) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> temp = new HashMap<>();
        //1、获取list01的map01
        for (int i = 0; i < list01.size(); i++) {
            Map<String, Object> map01 = list01.get(i);
            temp.putAll(map01);
            //2、获取map01的key
            Set<String> keySet01 = map01.keySet();
            //3、获取list02的map02
            for (int j = 0; j < list02.size(); j++) {
                Map<String, Object> map02 = list02.get(j);
                //4、获取map02的key
                Set<String> keySet02 = map02.keySet();
                loop:
                for (String key01 : keySet01) {
                    String value01 = (String) map01.get(key01);
                    for (String key02 : keySet02) {
                        String value02 = (String) map02.get(key02);
                        if (value01.equals(value02)) {
                            temp.putAll(map02);
                            list.add(temp);
                            temp = new HashMap<String, Object>();
                            break loop;
                        }
                    }
                }
            }
        }
        System.out.println(list);
        return list;
    }*/

    private List<Map<String, Object>> getMerge(List<Map<String, Object>> list01, List<Map<String, Object>> list02) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> temp = new HashMap<>();
        //1、获取list01的map01
        if (list01!=null&&!list01.isEmpty()) {
            for (int i = 0; i < list01.size(); i++) {
                Map<String, Object> map01 = list01.get(i);
                temp.putAll(map01);
                //2、获取map01的key
                Set<Map.Entry<String, Object>> entries01 = map01.entrySet();
                //3、获取list02的map02
                if (list01!=null&&!list02.isEmpty()) {
                    for (int j = 0; j < list02.size(); j++) {
                        Map<String, Object> map02 = list02.get(j);
                        //4、获取map02的key
                        Set<Map.Entry<String, Object>> entries02 = map02.entrySet();
                        loop:
                        for (Map.Entry<String, Object> entry01 : entries01) {
                            for (Map.Entry<String, Object> entry02 : entries02) {
                                if (entry01.equals(entry02)) {
                                    temp.putAll(map02);
                                    list.add(temp);
                                    temp = new HashMap<String, Object>();
                                    break loop;
                                }
                            }
                        }
                    }
                }else {
                    list.add(map01);
                }
            }
            System.out.println(list);
            return list;
        }else {
            return list;
        }
    }
}
