import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class test01 {
    public static void main(String[] args) {
        //testUTF8();
        //String mapToJSONStr = getMapToJSONStr();
        System.out.println("====================");
        String jsonToMap = "{\"table\":[\"$@1$\", \"$@1$\"]}";
        Map map = getJSONStrToMap(jsonToMap);
        System.out.println("====================");
        //testJsonStr();

        //jsonStrToArray();
        //testAndJson();
        //testTablName();
    }

    public static String getMapToJSONStr() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("company_id", "6000003");
        System.out.println("map：" + map);
        String result = JSONObject.toJSONString(map);
        System.out.println("result：" + result);
        return result;
    }

    public static Map getJSONStrToMap(String jsonStr) {
        int index = 1;
        for (int i = 0; i < jsonStr.length(); i++) {

        }
        Map object = (Map) JSON.parse(jsonStr);
        for (int i = 0; i < object.size(); i++) {
        }
        HashMap<String, String> map = new HashMap<>();
        JSONObject jsonMap = JSONObject.parseObject(jsonStr);
        JSONArray tablelist = jsonMap.getJSONArray("table");
        for (int i = 0; i < tablelist.size(); i++) {
            //if ()
            map.put("$@" + index++ + "$", (String) tablelist.get(i));
        }

        //System.out.println("3:Map：" + table);
        return object;
    }

    public static void testJsonStr() {
        String jsonStr01 = "{\n" + "\"company_full_name\":{\"val\":\"江苏滨建集团有限公司\",\"opr\":\"like\"}\n" + "}\n";

        String jsonStr = "{\"aaa\":\"111\"}";
        System.out.println("jsonStr:" + jsonStr);
        Object object = JSON.parse(jsonStr);
        System.out.println("object:" + object);
        if (object instanceof JSONObject) {
            System.out.println("true");
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonStr01);
        JSONObject jsonMap = jsonObject.getJSONObject("company_full_name");
        System.out.println(jsonMap);
        Object val = jsonMap.get("val");
        System.out.println("val:" + val);//字符串
        String s = JSON.toJSONString(val);
        System.out.println("s:" + s);
        Object join = jsonMap.get("join");
        System.out.println("join：" + join);
        if (val instanceof JSONObject) {
            System.out.println("Yes");
        }
    }

    public static void jsonStrToArray() {
        String jsonStr = "{\n" + "\"return_columns\":\"company_id\"}\n";
        System.out.println("11:" + jsonStr);
        //String jsonStr = "{\n" + "\"return_columns\":[\"company_id\",\"company_full_name\",\"reg_capital\",\"credit_code\"]\n" + "}\n";
        JSONObject oprMap = JSONObject.parseObject(jsonStr);
        //获取数据列
        StringBuilder sb = new StringBuilder();
        Object returnColumns = oprMap.get("return_columns");
        //System.out.println(returnColumns);//["company_id","company_full_name","reg_capital","credit_code"]

        if (returnColumns instanceof String) {
            String conditionval = (String) returnColumns;
            sb.append(conditionval);
        } else {
            JSONArray jsonArray = oprMap.getJSONArray("return_columns");
            sb.append((String) jsonArray.get(0));
            for (int i = 1; i < jsonArray.size(); i++) {
                sb.append(",").append((String) jsonArray.get(i));
            }
        }
        System.out.println("jsonStrToArray：" + sb.substring(0, sb.length()));
    }

    public static void testAndJson(String jsonStr) {
        Map result = null;
        String condition = null;

        String task = "{\n" +
                "   \"triple\":\"<行业><集成电路><公司？>\",\n" +
                "   \"opr\":[\n" +
                "       {\n" +
                "           \"type\":\"query_entity_by_relationship\",\n" +
                "           \"table\":\"stock_industry_relation\",\n" +
                "           \"return_columns\":[\"symbol\",\"style_name_l1\",\"style_name_l2\",\"style_name_l3\",\"style_name_l4\"],\n" +
                "           \"condition\":{\n" +
                "               \"and\":{\n" +
                "                   \"update_time\":{\"val\":[\"2019-11-07\",\"2019-11-07\"],\"opr\":\"bet\"},\n" +
                "                   \"or\":{\n" +
                "                       \"style_name_l1\":{\"val\":\"集成电路\",\"opr\":\"like\"},\n" +
                "                       \"style_name_l2\":{\"val\":\"集成电路\",\"opr\":\"like\"},\n" +
                "                       \"style_name_l3\":{\"val\":\"集成电路\",\"opr\":\"like\"},\n" +
                "                       \"style_name_l4\":{\"val\":\"集成电路\",\"opr\":\"like\"}\n" +
                "                   }\n" +
                "               }\n" +
                "           }\n" +
                "       }\n" +
                "       {\n" +
                "           \"type\":\"query_entity_property\",\n" +
                "           \"table\":\"stock_instholder\",\n" +
                "           \"return_columns\":[\"symbol\",\"instholder_name\"],\n" +
                "           \"condition\":{\n" +
                "               \"and\":{\n" +
                "                   \"update_time\":{\"val\":[\"2019-11-07\",\"2019-11-07\"],\"opr\":\"bet\"},\n" +
                "                   \"symbol\":{\"val\":\"$@1.symbol$\",\"opr\":\"eq\"}\n" +
                "               }\n" +
                "           }\n" +
                "       },\n" +
                "       {\n" +
                "           \"type\":\"result_join\",\n" +
                "           \"table\":[\"$@1$\",\"$@2$\"],\n" +
                "           \"join\":\"left\",\n" +
                "           \"return_columns\":[\"symbol\",\"instholder_name\",\"style_name_l1\",\"style_name_l2\",\"style_name_l3\",\"style_name_l4\",],\n" +
                "           \"condition\":{\n" +
                "               \"and\":{\n" +
                "                    \"$@1.symbol$\":{\"val\":\"$@2.symbol$\",\"opr\":\"eq\"}\n" +
                "               }\n" +
                "           }\n" +
                "       }\n" +
                "   ],\n" +
                "   \"result\":\"@3\"\n" +
                "}";
    }

    public void searchTest(String jsonStr) {
        String companyBascRsult = null;
        Map result = null;
        ArrayList<String> tableList = new ArrayList<String>();
        if (jsonStr != null) {
            JSONObject parseObject = JSONObject.parseObject(jsonStr);//转成Map集合
            JSONArray jsonArray = parseObject.getJSONArray("opr");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject oprMap = jsonArray.getJSONObject(i);
                tableList.add(oprMap.getString("table"));
                String join = oprMap.getString("join");
                String returnColumns = doColumns(oprMap);

                JSONObject conditionMap = oprMap.getJSONObject("condition");
                JSONObject andMap = conditionMap.getJSONObject("and");
                String condition = doCondition(andMap, result);
                String sql = doSql(returnColumns, tableList, join, condition);
                //调用dao
                //返回结果
            }
        } else {

        }
    }

    private String doSql(String returnColumns, ArrayList<String> tableList, String join, String condition) {
        StringBuffer sb = new StringBuffer();
        sb.append("select ").append(returnColumns).append(" from ");
        if (join != null && (join.equals("left") || join.equals("right"))) {
            sb.append(tableList.get(0)).append(" ").append(join).append(" join ").append(tableList.get(1)).append(" on ").append(condition);
        } else {
            sb.append(tableList.get(0)).append(" where ").append(condition);
        }
        return null;
    }

    private String doColumns(JSONObject oprMap) {
        StringBuffer sb = new StringBuffer();
        JSONArray columnsArr = oprMap.getJSONArray("return_columns");
        for (int i = 0; i < columnsArr.size(); i++) {
            sb.append(columnsArr.get(i)).append(",");
        }
        return (sb.substring(0, sb.length() - 1));
    }

    private static String doCondition(JSONObject andMap, Map result) {
        System.out.println(andMap);
        //是否需要判断json是否为集合
        Set<String> andKeys = andMap.keySet();//or,company_full_name的集合
        StringBuffer sb = new StringBuffer();
        //遍历集合里面的每一个Map集合
        for (String andKey : andKeys) {
            JSONObject conditionMap = andMap.getJSONObject(andKey);
            if (andKey.equals("or")) {
                sb.append(" AND ");
                Set<String> styleKeys = conditionMap.keySet();//{}
                //StringBuffer stringBuffer1 = new StringBuffer();
                for (String styleKey : styleKeys) {//style_name_l1/style_name_l2
                    JSONObject map = conditionMap.getJSONObject(styleKey);//{"opr":"like","val":"集成电路"}
                    /*String Nam = styleKey;
                    String Val = doConditionVal(map, result, Nam);//集成电路
                    String Opr = doOperator(map);//like*/
                    String qurey = doWhere(andKey, conditionMap, result);
                    System.out.println("qurey:" + qurey);
                    sb.append(qurey).append(" ").append(andKey).append(" ");
                }
                String orOpr = sb.substring(0, sb.length() - 4);
                sb = new StringBuffer(orOpr);
                System.out.println("orOpr:" + orOpr);
            } else {
                sb.append(" AND ");
                /*String Nam = andKey;
                String Val = doConditionVal(conditionMap, result, Nam);//集成电路
                String Opr = doOperator(conditionMap);//like*/
                String qurey = doWhere(andKey, conditionMap, result);
                sb.append(" ").append(qurey).append(" ");
            }
        }
        System.out.println("sb:" + sb.substring(5));
        return sb.substring(5);
    }

    //where条件封装
    private static String doWhere(String andKey, JSONObject conditionMap, Map result) {
        String Nam = andKey;
        String Val = doConditionVal(conditionMap, result, Nam);//集成电路
        String Opr = doOperator(conditionMap);
        StringBuffer stringBuffer = new StringBuffer();
        if (Opr == "=") {
            stringBuffer.append(Nam).append(" ").append(Opr).append(" ").append(Val);
        } else if (Opr == "LIKE") {
            stringBuffer.append(Nam).append(" ").append(Opr).append(" ").append(Val);
        } else if (Opr == "BETWEEN") {
            List<String> valList = JSONArray.parseArray(Val, String.class);
            stringBuffer.append(Nam).append(" ").append(Opr).append(" ").append(valList.get(0)).append(" AND ").append(valList.get(1));
        } else if (Opr == "IN") {
            stringBuffer.append(Nam).append(" (");
            List<String> valList = JSONArray.parseArray(Val, String.class);
            for (int i = 0; i < valList.size(); i++) {
                stringBuffer.append(Nam).append(" ").append(valList.get(i)).append(" ");
            }
            stringBuffer.append(") ");
        } else {
        }
        return stringBuffer.toString();
    }

    //opr条件获取
    private static String doOperator(JSONObject map) {
        String conditionOpr = map.getString("opr");
        HashMap<String, String> operatorMap = new HashMap<String, String>();
        operatorMap.put("eq", "=");
        operatorMap.put("bet", "BETWEEN");
        operatorMap.put("like", "LIKE");
        operatorMap.put("and", "AND");
        operatorMap.put("or", "OR");
        operatorMap.put("in", "IN");
        operatorMap.put("not", "NOT");
        return operatorMap.get(conditionOpr);
    }

    //condition条件获取
    public static String doConditionVal(JSONObject conditionMap, Map result, String Nam) {
        StringBuffer sb = new StringBuffer();
        Object object = conditionMap.get("val");
        if (object instanceof String) {
            String conditonVal = (String) object;
            if (result != null) {
                Set keys = result.keySet();
                for (Object key : keys) {
                    if (key.equals(Nam)) {
                        conditonVal = result.get(key).toString();
                    }
                }
            }
            sb.append("'%").append(conditonVal).append("%'");
        } else if (object instanceof JSONArray) {
            //是时间或其他的数组条件
            sb.append(object);
        }
        System.out.println("sb:" + sb);
        return sb.toString();
    }

    public static void test001() {
        //获取条件
    /*private static String doCondition1(JSONObject conditionMap) {
        Set<String> conditionKeys = conditionMap.keySet();
        StringBuilder sb = new StringBuilder();
        Object object = conditionMap.get("val");
        if (object instanceof String) {
            String conditonVal = (String) object;
            sb.append("'").append(conditonVal).append("'");
        } else if (object instanceof JSONArray) {
            //是时间或其他的数组条件
            sb.append(object);
        }
        System.out.println("sb:" + sb);
        return sb.toString();
    }*/

        //判断两个set集合元素是否一致
    /*private static boolean isSetEqual(Set set1) {
        HashSet<String> set = new HashSet<String>();
        set.add("val");
        set.add("opr");

        if (set1 == null || set1.size() != set.size() || set1.size() == 0) {
            return false;
        }
        Iterator ite1 = set1.iterator();
        Iterator ite = set.iterator();
        boolean isFullEqual = true;
        while (ite1.hasNext()) {
            if (!set.contains(ite1.next())) {
                isFullEqual = false;
            }
        }
        return isFullEqual;*/
    }

    public static void testUTF8() {
        //String utf = "CPI\u6307\u6570";
        //String utf = "CPI\u9a6c\u4e91";//马云
        String utf = "CPI\u4e07\u79d1";//万科
        String decode = null;
        try {
            decode = URLDecoder.decode(utf, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(decode);
    }

    public static void testTablName() {
        String jsonStr = "{\n" +
                "   {\n" +
                "       \"type\":\"result_join\",\n" +
                "       \"table\":[\"$@1$\",\"$@2$\"],\n" +
                "       join\":\"left\",\n" +
                "       \"return_columns\":[\"symbol\",\"instholder_name\",\"style_name_l1\",\"style_name_l2\",\"style_name_l3\",\"style_name_l4\",],\n" +
                "       \"condition\":{\n" +
                "           \"and\":{\n" +
                "               \"$@1.symbol$\":{\"val\":\"$@2.symbol$\",\"opr\":\"eq\"}\n" +
                "           }\n" +
                "       }\n" +
                "   }\n" +
                "}";
        testAndJson(jsonStr);

    }
}




