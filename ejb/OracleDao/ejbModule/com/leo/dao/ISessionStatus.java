package com.leo.dao;

import javax.ejb.Remote;

@Remote
public interface ISessionStatus {
	public void addData();
}
