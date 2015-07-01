package com.leo.session;

import javax.ejb.Remote;

@Remote
public interface ISessionStatus {
	public void addData();
}
