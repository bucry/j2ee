package com.framework.core.xml;

import java.util.ArrayList;
import java.util.List;

public class DocumentEntity {
	
	private int methodId;
	private String methodName;
	private List<ParmsEntity> parmsList = new ArrayList<ParmsEntity>();

	public int getMethodId() {
		return methodId;
	}

	public void add(ParmsEntity en) {
		parmsList.add(en);
	}
	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<ParmsEntity> getParmsList() {
		return parmsList;
	}

	public void setParmsList(List<ParmsEntity> parmsList) {
		this.parmsList = parmsList;
	}

}
