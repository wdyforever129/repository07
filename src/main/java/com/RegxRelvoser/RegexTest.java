package com.RegxRelvoser;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegexTest {

    @Test
    public void RegexTest01() {
        //区间判断
        IntervalUtil a = new IntervalUtil();
        String data_value = "2";
        String[] intervals = {"(-∞,-2%]", "(-2%,0%)", "[0%,2%)", "[2%,∞)"};
        for (String interval : intervals) {
            //boolean result = a.isInTheInterval("1", "(-∞,1)");
            if (a.isInTheInterval(data_value, interval)) {
                System.out.println("interval:" + interval);
            }
        }
        //System.out.println(result);
    }

    /**
     * where条件解析及封装
     */
    @Test
    public void ResolverTest02() {
        ResolverUtil resolverUtil = new ResolverUtil();
        //String interval = "((industry_name1 like \"%集成电路%\")||(industry_name2 like \"%集成电路%\"))&&" +
               // "(date = \"2019-10-10\")";
        String interval = "(company_name like:\"%万科\")&&(date =:\"2019-10-10\")";
        List<Map> valList = new ArrayList<>();//查询条件
        List<String> oprList = new ArrayList<>();//查询操作符
        StringBuffer buff = new StringBuffer("where ");
        resolverUtil.getInterval(interval, valList, oprList, buff);
        //resolverUtil.getSql(valList, oprList);
        System.out.println("buff :" + buff);
    }

    /**
     * @ClassName: IntervalUtil
     */
    public static class IntervalUtil {

        /**
         * 判断data_value是否在interval区间范围内
         *
         * @param data_value 数值类型的
         * @param interval   正常的数学区间，包括无穷大等，如：(1,3)、>5%、(-∞,6]、(125%,135%)U(70%,80%)
         * @return true：表示data_value在区间interval范围内，false：表示data_value不在区间interval范围内
         * @author: kangyl17909
         * @date: 2018年7月3日
         */
        public boolean isInTheInterval(String data_value, String interval) {
            //将区间和data_value转化为可计算的表达式
            String formula = getFormulaByAllInterval(data_value, interval, "||");
            ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
            //System.out.println("formula:" + formula);
            try {
                //计算表达式
                return (Boolean) jse.eval(formula);
            } catch (Exception t) {
                return false;
            }
        }

        /**
         * 将所有阀值区间转化为公式：如
         * [75,80) =》  date_value >= 75 && date_value < 80
         * (125%,135%)U(70%,80%) =》 (date_value < 1.35 && date_value > 1.25) || (date_value < 0.8 && date_value > 0.7)
         *
         * @param data_value
         * @param interval   形式如：(125%,135%)U(70%,80%)
         * @param connector  连接符 如：") || ("
         */
        private String getFormulaByAllInterval(String data_value, String interval, String connector) {
            StringBuffer buff = new StringBuffer();
            for (String limit : interval.split("U")) {//如：（125%,135%）U (70%,80%)
                buff.append("(").append(getFormulaByInterval(data_value, limit, " && ")).append(")").append(connector);
            }
            String allLimitInvel = buff.toString();
            int index = allLimitInvel.lastIndexOf(connector);
            allLimitInvel = allLimitInvel.substring(0, index);
            return allLimitInvel;
        }

        /**
         * 将整个阀值区间转化为公式：如
         * 145)    =》 date_value < 145
         * [75,80) =》 date_value < 80 && date_value >= 75
         *
         * @param data_value
         * @param interval   形式如：145)、[75,80)
         * @param connector  连接符 如：&&
         */
        private String getFormulaByInterval(String data_value, String interval, String connector) {
            StringBuffer buff = new StringBuffer();
            for (String halfInterval : interval.split(",")) {//如：[75,80)、≥80
                buff.append(getFormulaByHalfInterval(halfInterval, data_value)).append(connector);
            }
            String limitInvel = buff.toString();
            int index = limitInvel.lastIndexOf(connector);
            limitInvel = limitInvel.substring(0, index);
            return limitInvel;
        }

        /**
         * 将半个阀值区间转化为公式：如
         * 145)  =》 date_value < 145
         * ≥80% =》 date_value >= 0.8
         * [130  =》 date_value >= 130
         * <80%  =》 date_value < 0.8
         *
         * @param halfInterval 形式如：145)、≥80%、[130、<80%
         * @param data_value
         * @return date_value < 145
         */
        private String getFormulaByHalfInterval(String halfInterval, String data_value) {
            halfInterval = halfInterval.trim();
            if (halfInterval.contains("∞")) {//包含无穷大则不需要公式
                return "1 == 1";
            }
            StringBuffer formula = new StringBuffer();
            String data = "";//截取出数据80%
            String opera = "";//截取操作符≥
            //截取符合在前面的情况
            if (halfInterval.matches("^([<>≤≥\\[\\(]{1}(-?\\d+.?\\d*\\%?))$")) {//表示判断方向（如>）在前面 如：≥80%
                opera = halfInterval.substring(0, 1);
                data = halfInterval.substring(1);
                //截取符号在后面的情况
            } else {//[130、145)
                opera = halfInterval.substring(halfInterval.length() - 1);//截取出数据
                data = halfInterval.substring(0, halfInterval.length() - 1);//截取操作符≥
            }
            double value = dealPercent(data);
            double dataValue = dealPercent(data_value);
            formula.append(dataValue).append(" ").append(opera).append(" ").append(value);
            String a = formula.toString();
            //转化特定字符
            return a.replace("[", ">=").replace("(", ">").replace("]", "<=").replace(")", "<").replace("≤", "<=").replace("≥", ">=");
        }

        /**
         * 去除百分号，转为小数
         *
         * @param str 可能含百分号的数字
         * @return
         */
        private double dealPercent(String str) {
            double d = 0.0;
            if (str.contains("%")) {
                str = str.substring(0, str.length() - 1);
                d = Double.parseDouble(str) / 100;
            } else {
                d = Double.parseDouble(str);
            }
            return d;
        }
    }

    /**
     * @ClassName: IntervalUtil
     */
    public static class ResolverUtil {
        /**
         * 解析表达式：((industry_name1:"%集成电路%")||(industry_name2:"%集成电路%")）&&(date:"=2019-10-10")
         * @param interval
         * @return
         */
        public void getInterval(String interval, List valList, List oprList, StringBuffer buff) {
            //将区间和data_value转化为可计算的表达式
            getFormulaByAllInterval(interval, valList, oprList, buff);
            System.out.println("valList：" + valList);
            System.out.println("oprList：" + oprList);
        }

        /**
         * 将所有阀值区间转化为公式：如
         * ((industry_name1:"%集成电路%")||(industry_name2:"%集成电路%"))&&(date:"=2019-10-10")=》
         *
         * @param interval
         */
        private void getFormulaByAllInterval(String interval, List valList, List oprList, StringBuffer buff) {
            if (interval.contains("))&&") || interval.contains("&&((")) {
                for (String limit : interval.split("&&")) {//((industry_name1:"%集成电路%")||(industry_name2:"%集成电路%")) (date:"=2019-10-10")
                    if (limit.contains("((")) {
                        limit = limit.substring(1, limit.length() - 1);
                    }
                    getFormulaByInterval(limit, valList, oprList, buff);
                    buff.append(" and ");
                }
                buff.delete(buff.length()-5,buff.length());
                //String opr = getOpr("&&");
                //oprList.add(opr);
            }else {
                getFormulaByInterval(interval, valList, oprList, buff);
            }
        }

        /**
         * 获取键值对()
         * (industry_name1:"%集成电路%")||(industry_name2:"%集成电路%")=》(industry_name1:"%集成电路%") (industry_name2:"%集成电路%")
         *
         * @param interval 形式如：145)、[75,80)
         */
        /*private void getFormulaByInterval(String interval, List valList, List oprList, StringBuffer buff) {
            if (interval.contains("||")) {//(industry_name1:"%集成电路%")||(industry_name2:"%集成电路%")
                buff.append("( ");
                for (String halfInterval : interval.split("\\|\\|")) {//(industry_name1:"%集成电路%")
                    getFormulaByHalfInterval(halfInterval, valList, buff);
                    buff.append(" or ");
                }
                buff.delete(buff.length()-4,buff.length());
                buff.append(" )");
                //String opr = getOpr("||");
                //oprList.add(opr);

            } else if (interval.contains("&&")) {//(industry_name1:"%集成电路%")&&(industry_name2:"%集成电路%")
                for (String halfInterval : interval.split("&&")) {//(industry_name1:"%集成电路%")
                    getFormulaByHalfInterval(halfInterval, valList, buff);
                    buff.append(" and ");
                }
                buff.delete(buff.length()-5,buff.length());
                //String opr = getOpr("&&");
                //oprList.add(opr);
            } else {//(date:"=2019-10-10")
                getFormulaByHalfInterval(interval, valList, buff);
            }
        }*/
        private void getFormulaByInterval(String interval, List valList, List oprList, StringBuffer buff) {
            if (interval.contains("||")) {//(industry_name1:"%集成电路%")||(industry_name2:"%集成电路%")
                buff.append("( ");
                for (String halfInterval : interval.split("\\|\\|")) {//(industry_name1:"%集成电路%")
                    halfInterval = halfInterval.substring(1, halfInterval.length() - 1);
                    buff.append(halfInterval).append(" or ");
                }
                buff.delete(buff.length()-4,buff.length());
                buff.append(" )");
                //String opr = getOpr("||");
                //oprList.add(opr);
            } else if (interval.contains("&&")) {//(industry_name1:"%集成电路%")&&(industry_name2:"%集成电路%")
                for (String halfInterval : interval.split("&&")) {//(industry_name1:"%集成电路%")
                    halfInterval = halfInterval.substring(1, halfInterval.length() - 1);
                    buff.append(halfInterval).append(" and ");
                }
                buff.delete(buff.length()-5,buff.length());
                //String opr = getOpr("&&");
                //oprList.add(opr);
            } else {//(date:"=2019-10-10")
                interval = interval.substring(1,interval.length()-1);
                buff.append(interval);
            }
        }

        /**
         * 处理(industry_name1:"%集成电路%")=》(industry_name1 "%集成电路%")
         * @param halfInterval 形式如：145)、≥80%、[130、<80%
         * @return date_value < 145
         */
        private void getFormulaByHalfInterval(String halfInterval, List valList, StringBuffer buff) {//(industry_name1:"%集成电路%")
            //halfInterval = halfInterval.trim();//删除任何前导和尾缀、空格
            System.out.println("halfInterval：" + halfInterval);
            String[] splits = halfInterval.split(":");
            getFormulaByHalfandHalfInterval(splits, valList, buff);
        }

        /**
         * 处理(industry_name =》 industry_name
         * @param splits
         * @param valList
         * @param buff
         */
        private void getFormulaByHalfandHalfInterval(String[] splits, List valList, StringBuffer buff) {//(industry_name  "%集成电路%")
            Map<String, Object> map = new HashMap<>();
            String condition = "";
            String val = "";
            //截取符合在前面的情况
            for (String split : splits) {
                //if (split.matches("^\\(")) {//表示判断方向（如>）在前面 如：(industry_name
                if (split.contains("(")) {
                    condition = split.substring(1);//industry_name
                    buff.append(condition);
                    //截取符号在后面的情况
                } else {//"%集成电路%")
                    val = split.substring(0, split.length() - 1);//截取)  "%集成电路%"
                    buff.append(val);
                }
            }
            map.put(condition, val);
            valList.add(map);
        }

        /**
         * 获取操作符
         */
        /*public String getOpr(String opr) {
            Map<String, String> map = new HashMap<>();
            map.put("||", "or");
            map.put("&&", "and");
            return map.get(opr);
        }

        public void getSql(List<Map> valList, List<String> oprList) {
            StringBuffer buffer = new StringBuffer("where ");
            for (int i = 0; i < valList.size(); i++) {

                buffer.append(valList.get(i).keySet()).append(valList.get(i).values()).append(" ");
                if (i < i ) {
                    buffer.append(oprList.get(i));
                }
            }
            System.out.println(buffer.toString());
        }*/
    }

    /**
     *
     */
    @Test
    public void RegexTest() {
        String a="abcdefg<img src=\"0001.jpg\"/>hijklmnopq";
        String b=a.replaceAll("<img[^/>]*/>","替换标签");//用正则表达式
        System.out.println(b);
    }
}
