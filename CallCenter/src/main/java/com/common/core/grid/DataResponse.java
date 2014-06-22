package com.common.core.grid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataResponse<T> {

    //数据源字段名
    @JsonProperty("Rows")
    private List<T> Rows = new ArrayList<T>();

    //数据源记录数字段名
    @JsonProperty("Total")
    private long Total;

    public DataResponse(Page<T> page) {
        this.Rows = new ArrayList<T>();
        this.Rows.addAll(page.getContent());
        this.Total = page.getTotalElements();
    }

    public DataResponse() {
    }

    public DataResponse(Collection<T> rows) {
        this.Rows = new ArrayList<T>();
        this.Rows.addAll(rows);
        this.Total = rows.size();
    }

    public DataResponse(T entity) {
        this.Rows = new ArrayList<T>();
        this.Rows.add(entity);
    }

    @JsonIgnore
    public List<T> getRows() {
        return Rows;
    }

    @JsonIgnore
    public void setRows(List<T> rows) {
        Rows = rows;
    }

    @JsonIgnore
    public long getTotal() {
        return Total;
    }

    @JsonIgnore
    public void setTotal(long total) {
        Total = total;
    }

	@Override
	public String toString() {
		return "DataResponse [Rows=" + Rows + ", Total=" + Total + "]";
	}
    
    
}
