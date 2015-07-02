package com.leo.dao;

import javax.ejb.Remote;

@Remote
public interface NoStatus {
	public String sayHello(String name);
}
