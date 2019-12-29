package com.RegxRelvoser;

import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegxToDate {
    @Test
    public void Test01() {
        String date = "the_date bet ['2019-10-01', '2019-10-10']";
        StringBuffer buffer = new StringBuffer();
        if (!isDate(date)) {
            List dateList = getRegx01(date);
            //if (!dateList.isEmpty()) {
            String split = date.split(" bet ")[0];
            date = buffer.append(split).append(" BETWEEN ").append(dateList.get(0)).append(" AND ").append(dateList.get(1)).toString();
            System.out.println(date);
        } else {
            //TODO 调用下面的方法，查询日期封装
        }
    }

    /**
     * 正则表达式获取字符串中的时间
     */
    public List getRegx01(String date) {
        String regx = "\\d{4}[-]\\d{1,2}[-]\\d{1,2}";
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(date);
        boolean matches = matcher.matches();
        System.out.println(matches);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        System.out.println(list);
        return list;
    }

    public Boolean isDate(String date) {
        String regex = "(\\d{4}-\\d{1,2}-\\d{1,2})";
        Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(date);
        boolean matches = matcher.matches();
        System.out.println(matches);
        HashMap<Object, Object> map = new HashMap<>();

        Set<Map.Entry<Object, Object>> entries = map.entrySet();
        return matches;

    }

    /**
     * 正则表达式获取字符串中的时间
     */
    @Test
    public void getRegx03() {
        String s ="<date:2y/>";
        String regx = "<([^<>]*)/";
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(s);
        boolean matches = matcher.matches();
        System.out.println(matches);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        System.out.println(list);
        //return list;
    }
}
