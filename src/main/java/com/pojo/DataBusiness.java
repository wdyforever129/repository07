package com.pojo;

import java.util.List;

public class DataBusiness {
    private String type;
    private List table;
    private List return_columns;
    private String condition;
    private List order_by;
    private Integer size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List getTable() {
        return table;
    }

    public void setTable(List table) {
        this.table = table;
    }

    public List getReturn_columns() {
        return return_columns;
    }

    public void setReturn_columns(List return_columns) {
        this.return_columns = return_columns;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List getOrder_by() {
        return order_by;
    }

    public void setOrder_by(List order_by) {
        this.order_by = order_by;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "DataBusiness{" +
                "type='" + type + '\'' +
                ", table=" + table +
                ", return_columns=" + return_columns +
                ", condition='" + condition + '\'' +
                ", order_by=" + order_by +
                ", size=" + size +
                '}';
    }
}
