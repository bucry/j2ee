package com.leo.dao;

import javax.ejb.Remote;

@Remote
public interface HelloChinaRemote {
	public String sayHello(String name);
	public String Myname();
}