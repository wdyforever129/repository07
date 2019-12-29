package com.RegxRelvoser;

import com.util.RegexUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RegexTest02 {


    @Test
    public void regexTest() {
        RegexUtil regexUtil = new RegexUtil();

        //String task_con = "(<@4.instrument_id/> = <@3.instrument_id/>) & (<@3.instrument_id/> = <@2.instrument_id/>) & (<@2.instrument_id/> = <@1.instrument_id/>)";
        String task_con = "(instrument_id = instrument_id) & (instrument_id = instrument_id)";
        List<String> tables = new ArrayList<>();
        tables.add("@3");
        tables.add("@2");
        tables.add("@1");

        String[] s = task_con.split("\\(\\.*?\\)");
        System.out.println(s[0]);

        //String regex = "(?<=\\<)[^\\/]+";//不包含<>尖括号

        //String regex = "(\\(.*?\\))";//匹配每对()内的内容
        String regex = "(\\(.*?\\))";//匹配每对()内的内容
        List taskConList = regexUtil.getRegexList(task_con, regex);

        System.out.println(taskConList);


        /*String regex = "\\(.*?\\)"; //包含括号;\<.*?\/:取中括号内容；(?<=\<)[^\/]+:取中括号内容
        //String regex = "'(.*?)'";  //不包含括号
        List list = getRegexUtil(task_con, regex);
        System.out.println(list);*/


    }
}
