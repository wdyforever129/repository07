package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public List getRegexList(String str, String regex) {
        ArrayList<Object> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    public String getRegex(String str, String regex) {
        ArrayList<Object> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        return (matcher.group(1));
    }

    /**
     * 通过正则匹配字符串中指定的一个子字符串
     * @param str 源字符串
     * @param regex 正则表达式
     * @return
     */
    public String getRegexSub(String str, String regex) {
        //Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        String group = matcher.group();
        return group;
    }
}
