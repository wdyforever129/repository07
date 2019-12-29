import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import scala.util.matching.Regex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonAndJava {

    @Test
    public void getJsonToJava() {
        String jsonStr = "{\n" +
                "\t\"request\":\"success\",\n" +
                "\t\"age\":18,\n" +
                "\t\"school\":\"清华大学\"\n" +
                "}";
        JSONObject json = new JSONObject();
    }

    public void getUnicodeToString() {
        //unicodeToString

    }

    /// <summary>
    /// 获得字符串中开始和结束字符串中间得值
    /// </summary>
    /// <param name="str">字符串</param>
    /// <param name="s">开始</param>
    /// <param name="e">结束</param>
    /// <returns></returns>
    /*public static String GetValue(String str, String s, String e) {
        Regex rg = new Regex("(?<=(" + s + "))[.\\s\\S]*?(?=(" + e + "))", RegexOptions.Multiline | RegexOptions.Singleline);
        return rg.Match(str).Value;
    }*/

    // 解析json(最复杂的解析方法)
    @Test
    public void jsonToJava() {
        //为了避免二义性，字符串里面的双印号使用了\转义字符
        String jsonStr = "{\n" +
                "   \"errorCode\":\"0\",\n" +
                "   \"errorMsg\" :\"调用接口成功\",\n" +
                "   \"data\":[\n" +
                "       {\"username\":\"张文哲\",\"position\":\"student\"}\n" +
                "   ]\n" +
                "}";

        //存储tableName
        //存储数据列
        //存储条件值
        //存储查询结果
        Map<String, String> conditionMap = new HashMap<>();

        // 将json格式的字符串转换成json
        JSONObject root = new JSONObject().parseObject(jsonStr);
        //定义一个字符串，其值为根据json对象中的数据名解析出的其所对应的值
        String errorCode = root.getString("errorCode");
        String errorMsg = root.getString("errorMsg");
        System.out.println("errorCode = " + errorCode + "; errorMsg = " + errorMsg);
        //根据json对象中数组的名字解析出其所对应的值
        JSONArray dataArr = root.getJSONArray("data");
        System.out.println("dataArr = " + dataArr);

        //对解析出的数组进行遍历
        for (int i = 0; i < dataArr.size(); i++) {
            //得到数组中对应下标对应的json对象
            JSONObject dataBean = (JSONObject) dataArr.get(i);
            //根据json对象中的数据名解析出相应数据
            String username = dataBean.getString("username");
            String position = dataBean.getString("position");
            String join = dataBean.getString("join");
            conditionMap.put("username", username);
            conditionMap.put("position", position);
            //打印输出
            System.out.println(username);
            System.out.println(position);
            System.out.println(join);
            System.out.println(conditionMap + ", " + conditionMap.get("username") + ", " + conditionMap.get("position"));

            String response = null;
            JSONObject jsonObject = JSONObject.parseObject(response);
            JSONArray jsonArray = (JSONArray) ((JSONObject) jsonObject.get("content")).get("rows");
            List<Map<String, Object>> list = JSONArray.parseObject(jsonArray.toJSONString(), List.class);


        }
    }
}
