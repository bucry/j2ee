package com.bucry.json.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonResponse implements Serializable {

	private static final long serialVersionUID = 6624045846054205235L;
	private Integer pageSize;
    private Integer pageNo;
    private String table;
    private String operation = "update";
    private String className = CommonResponse.class.getName();
    private List<RowResponse> result = new ArrayList<RowResponse>();

    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void add(RowResponse resultInfo) {
        result.add(resultInfo);
    }

    public void setResult(List<RowResponse> info) {
        this.result = info;
    }

    public List<RowResponse> getResult() {
        return result;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return  pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
    public Integer getPageNo() {
        return pageNo;
    }

    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

}
