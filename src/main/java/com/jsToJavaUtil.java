package com;

import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class jsToJavaUtil {
    public static void main(String[] args) {
        String num = "1";
        String num2 = "22";
        //开始调用时间
        long start = System.currentTimeMillis();
        String max = js(num, num2, "max");
        String min = js(num, num2, "min");
        System.out.println(">>>>>>>>>>>调用总耗时:" + (System.currentTimeMillis() - start));
        System.out.println("max: " + max);
        System.out.println("min: " + min);
    }

    private static String js(String num, String num2, String type) {
        String back = null;
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");

        try {

            String script = "function test(n1,n2) {" +
                    "function max(n1,n2) {" +
                    "if(n1>n2){return n1}" +
                    "else {return n2}" +
                    "};" +
                    "function min(n1,n2) {" +
                    "if(n1<n2){return n1}" +
                    "else {return n2}" +
                    "};" +
                    "var flag = '" + type + "';" +
                    "if(flag == 'max'){return max('" + num + "','" + num2 + "')}" +
                    "else if(flag == 'min'){return min('" + num + "','" + num2 + "')}" +
                    "}";
            String regex = "\\[(.*?)]";
            Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
            Matcher matcher = pattern.matcher(script);
            String group = matcher.group();
            System.out.println("group:" + group);
            se.eval(script);
            //System.out.println(jsResult);
            Invocable inv = (Invocable) se;
            back = (String) inv.invokeFunction("test", num);
        } catch (Exception e) {
            System.out.println("java执行失败！");
            e.printStackTrace();
        }
        return back;
    }

    @Test
    public void testReg() {
        String str = "function(a='@1.name', b='@2.name')(sss)";
        //Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
        Pattern pattern1 = Pattern.compile("\\(.*?\\)");
//        Matcher matcher = pattern1.matcher(str);
//         Pattern pattern1 = Pattern.compile("(?<=\\()[^)]+(?=\\))");
        //boolean matches = Pattern.matches("\\((.*?)\\)", "(asassas=b,asas=a)fun(fdf)(0)");
        Matcher matcher1 = pattern1.matcher(str);
        matcher1.find();
        String group = matcher1.group();
        Pattern pattern2 = Pattern.compile("(\\w+) *= *'(@.*?)'");
        Matcher matcher2 = pattern2.matcher(group);

       List<String> list_formal = new ArrayList<>();
       List<String> list_actual = new ArrayList<>();
        while (matcher2.find()) {
            list_formal.add(matcher2.group(1));
            list_actual.add("<" + matcher2.group(2) + "/>");
        }
        System.out.println(list_formal);

        String str_formal = list_formal.toString().replace("[", "(").replace("]", ")");
        String function_body = str.substring(0, matcher1.start()) + str_formal + str.substring(
                        matcher1.end(), str.length());
        System.out.println(list_actual);
        System.out.println(function_body);
    }
}

