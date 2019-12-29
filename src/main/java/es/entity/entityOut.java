package es.entity;

import java.util.List;

public class entityOut {
    private List source;
    private String query;
    private String sort;
    private String aggs;
    private Integer size;

    public List getSource() {
        return source;
    }

    public void setSource(List source) {
        this.source = source;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAggs() {
        return aggs;
    }

    public void setAggs(String aggs) {
        this.aggs = aggs;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "entityOut{" +
                "source=" + source +
                ", query='" + query + '\'' +
                ", sort='" + sort + '\'' +
                ", aggs='" + aggs + '\'' +
                ", size=" + size +
                '}';
    }
}
