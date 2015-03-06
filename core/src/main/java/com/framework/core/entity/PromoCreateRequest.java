package com.framework.core.entity;

import java.io.Serializable;

public class PromoCreateRequest implements Serializable {

	private static final long serialVersionUID = -4285282810710421801L;
	
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
