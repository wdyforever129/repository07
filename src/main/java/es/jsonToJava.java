package es;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class jsonToJava {

    @Test
    public void relvoserEs(){
        String jsonStr ="{\n" +
                "\t\"GET kg_askbob_entity_ins_pagroup/_search\":{\n" +
                "\t\t\"_source\":[\"pagroup_code\",\"pagroup_name\"],\n" +
                "\t\t\"query1\":{\n" +
                "\t\t\t\"multi_match\":{\n" +
                "\t\t\t\t\"query2\":\"银行\",\n" +
                "\t\t\t\t\"type\":\"best_fields\",\n" +
                "\t\t\t\t\"fields\":[\"pagroup_name\",\"pagroup_short_name\"]\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}\n";

        JSONObject parseObject = JSONObject.parseObject(jsonStr);
        System.out.println(parseObject);

    }
}
