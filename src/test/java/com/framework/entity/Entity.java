package com.framework.entity;

import java.io.Serializable;

public class Entity implements Serializable {
	
	private int id;
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Entity pr = (Entity) obj;
        return name != null ? name.equals(pr.name) : pr.name == null;
    }

}
