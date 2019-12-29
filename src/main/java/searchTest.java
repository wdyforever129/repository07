import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.*;


public class searchTest {
    public static void main(String[] args) {

        /*String jsonStr = "{\n" +
                "\t\"triple\": \"\",\n" +
                "\t\"opr\": [{\n" +
                "\t\t\"type\": \"query_entity_property\",\n" +
                "\t\t\"table\": \"macro_indicinfo\",\n" +
                "\t\t\"return_columns\": [\"indicname\", \"indictype\"],\n" +
                "\t\t\"condition\": {\n" +
                "\t\t\t\"and\": {\n" +
                "\t\t\t\t\"indicname\": {\n" +
                "\t\t\t\t\t\"val\": \"CPI\\u6307\\u6570\",\n" +
                "\t\t\t\t\t\"opr\": \"like\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}],\n" +
                "\t\"result\": \"@2\",\n" +
                "\t\"format\": \"?\"\n" +
                "}";*/
        String jsonStr = "{\n" +
                "\t\"triple\":\"\",\n" +
                "\t\"opr\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"type\":\"query_entity_property\",\n" +
                "\t\t\t\"table\":\"Neo4j\",\n" +
                "\t\t\t\"return_columns\":[\"ORG\",\"STOCKHOLDER\",\"HOLD\"],\n" +
                "\t\t\t\"condition\":{\n" +
                "\t\t\t\t\"and\":{\n" +
                "\t\t\t\t\t\"STOCKHOLDER\":{\"val\":\"马云\",\"opr\":\"like\"},\n" +
                "\t\t\t\t\t\"ORG\":{\"val\":\"万科\",\"opr\":\"like\"}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"result\":\"@2\",\n" +
                "\t\"format\":\"?\"\n" +
                "}";
        searchTest(jsonStr);
    }

    public static void searchTest(String jsonStr) {
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
                System.out.println("sql:" + sql);
                //返回结果
            }
        } else {
        }
    }

    private static String doSql(String returnColumns, ArrayList<String> tableList, String join, String condition) {
        StringBuffer sb = new StringBuffer();
        sb.append("select ").append(returnColumns).append(" from ");
        if (join != null && (join.equals("left") || join.equals("right"))) {
            sb.append(tableList.get(0)).append(" ").append(join).append(" join ").append(tableList.get(1)).append(" on ").append(condition);
        } else {
            sb.append(tableList.get(0)).append(" where ").append(condition);
        }
        return sb.toString();
    }

    private static String doColumns(JSONObject oprMap) {
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
                    String qurey = doWhere(andKey, conditionMap, result);
                    System.out.println("qurey:" + qurey);
                    sb.append(qurey).append(" ").append(andKey).append(" ");
                }
                String orOpr = sb.substring(0, sb.length() - 4);
                sb = new StringBuffer(orOpr);
                System.out.println("orOpr:" + orOpr);
            } else {
                sb.append(" AND ");
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
}
