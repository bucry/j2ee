package com.bucry.json.response;

import java.io.Serializable;

public class RowDetails implements Serializable {

	private static final long serialVersionUID = -1815802817251119000L;
	private String key;
    private String value;
    private String className = RowDetails.class.getName();

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

}
