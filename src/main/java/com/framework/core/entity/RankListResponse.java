package com.framework.core.entity;

import java.io.Serializable;

public class RankListResponse implements Serializable {
	
	private static final long serialVersionUID = 1874299935133936736L;
	
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
