package com.bucry.json.response;

import java.util.ArrayList;
import java.util.List;

public class RowResponse {

    private Integer id;

    private String className = RowResponse.class.getName();

    private List<RowDetails> results = new ArrayList<RowDetails>();


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void add(RowDetails info) {
        results.add(info);
    }

    public void setResults(List<RowDetails> results) {
        this.results = results;
    }

    public List<RowDetails> getResults() {
        return results;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
