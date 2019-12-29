package com;

import com.pojo.DataBusiness;
import com.pojo.Databases;
import com.util.JsonToJava;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class relvoser {
    Databases databases = new Databases();
    List<DataBusiness> oprList = new ArrayList<DataBusiness>();

    @Test
    public void resolver() {
        String task = "{\n" +
                "\t\"triple\": \"\",\n" +
                "\t\"opr\": [{\n" +
                "\t\t\t\"type\": \"query_id\",\n" +
                "\t\t\t\"table\": [\"dds_pyxis_stock_info\"],\n" +
                "\t\t\t\"return_columns\": [\"instrument_id\", \"stock_name\"],\n" +
                "\t\t\t\"condition\": \"(stock_name like  \\\\u4e2d\\\\u56fd\\\\u5e73\\\\u5b89)\",\n" +
                "\t\t\t\"order_by\": [],\n" +
                "\t\t\t\"size\": -1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"type\": \"query_entity_attr\",\n" +
                "\t\t\t\"table\": [\"dds_pyxis_stock_info\"],\n" +
                "\t\t\t\"return_columns\": [\"instrument_id\", \"total_liabilities\", \"the_date\"],\n" +
                "\t\t\t\"condition\": \"(instrument_id =<@1.instrument_id/>)\",\n" +
                "\t\t\t\"order_by\": [\"the_date DESC\"],\n" +
                "\t\t\t\"size\": 1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"type\": \"return_join\",\n" +
                "\t\t\t\"table\": [\"@1\", \"@2\"],\n" +
                "\t\t\t\"return_columns\": [\"*\"],\n" +
                "\t\t\t\"condition\": \"(<@1.instrument_id/>=<@2.instrument_id/>)\",\n" +
                "\t\t\t\"order_by\": [],\n" +
                "\t\t\t\"size\": -1\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"format\": [\"text\"],\n" +
                "\t\"result\": [\"<@3.stock_name/>(<@3.instrument_id/>)\\\\u603b\\\\u8d44\\\\u4ea7\\\\u8d1f\\\\u503a\\\\u4e3a<@3.toal_liabilities/>\"]\n" +
                "}";
        int cont = 1;
        String sql = null;
        Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();
        /**
         * 解析数据
         */
        databases = JsonToJava.parseObject(task, Databases.class);
        oprList = JsonToJava.parseArray(databases.getOpr(), DataBusiness.class);
        for (DataBusiness dataBusiness : oprList) {
            StringBuffer buffer = new StringBuffer();
            if (dataBusiness.getType().contains("query")) {
                buffer.append("select ");
                String column = getReturnColumns(dataBusiness.getReturn_columns());
                List table = dataBusiness.getTable();
                String condition = getCondition(dataBusiness.getCondition());
                String order = getOrder(dataBusiness.getOrder_by());
                Integer size = dataBusiness.getSize();
                System.out.println(condition);
                if (table.size() == 1) {
                    sql = getSql(column, (String) table.get(0), condition, order, size);
                }
            } else if (dataBusiness.getType().equals("result_join")) {

            }
        }
        Map<String, Object> result = query(sql);
        resultMap.put("<@" + cont++, result);

    }

    private Map<String, Object> query(String sql) {

        return null;
    }

    /**
     * 封装sql语句
     *
     * @param column
     * @param tableName
     * @param condition
     * @param order
     * @param size
     * @return
     */
    private String getSql(String column, String tableName, String condition, String order, Integer size) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select ").append(column).append(" from ").append(tableName).append(" where ").append(condition);
        if (order != null) {
            buffer.append(" ").append(order);
        }
        if (size != -1) {
            buffer.append("limit").append(size);
        }
        return buffer.toString();
    }

    /**
     * 获取数据列
     *
     * @param columnList
     * @return
     */
    private static String getReturnColumns(List columnList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < columnList.size(); i++) {
            sb.append(columnList.get(i)).append(",");
        }
        return (sb.substring(0, sb.length() - 1));
    }

    /**
     * 获取where条件
     *
     * @param condition
     * @return
     */
    private static String getCondition(String condition) {
        StringBuffer buff = new StringBuffer();
        if (condition.contains("))&&") || condition.contains("&&((")) {
            for (String limit : condition.split("&&")) {//((industry_name1:"%集成电路%")||(industry_name2:"%集成电路%")) (date:"=2019-10-10")
                if (limit.contains("((")) {
                    limit = limit.substring(1, limit.length() - 1);
                }
                getFormulaByInterval(limit, buff);
                buff.append(" and ");
            }
            buff.delete(buff.length() - 5, buff.length());
        } else {
            getFormulaByInterval(condition, buff);
        }
        return buff.toString();
    }

    private static void getFormulaByInterval(String interval, StringBuffer buff) {
        if (interval.contains("||")) {//(industry_name1:"%集成电路%")||(industry_name2:"%集成电路%")
            buff.append("( ");
            for (String halfInterval : interval.split("\\|\\|")) {//(industry_name1:"%集成电路%")
                halfInterval = halfInterval.substring(1, halfInterval.length() - 1);
                buff.append(halfInterval).append(" or ");
            }
            buff.delete(buff.length() - 4, buff.length());
            buff.append(" )");
        } else if (interval.contains("&&")) {//(industry_name1:"%集成电路%")&&(industry_name2:"%集成电路%")
            for (String halfInterval : interval.split("&&")) {//(industry_name1:"%集成电路%")
                halfInterval = halfInterval.substring(1, halfInterval.length() - 1);
                buff.append(halfInterval).append(" and ");
            }
            buff.delete(buff.length() - 5, buff.length());
        } else {//(date:"=2019-10-10")
            interval = interval.substring(1, interval.length() - 1);
            buff.append(interval);
        }
    }

    private static void getVal(String halfInterval){
        String[] half = halfInterval.split(".");

    }

    /**
     * 获取排序字段
     *
     * @param orderList
     * @return
     */
    private static String getOrder(List orderList) {
        StringBuffer sb = new StringBuffer();
        if (orderList != null && orderList.size() != 0) {
            for (int i = 0; i < orderList.size(); i++) {
                sb.append(orderList.get(i)).append(",");
            }
            return (sb.substring(0, sb.length() - 1));
        }
        return null;
    }
}


