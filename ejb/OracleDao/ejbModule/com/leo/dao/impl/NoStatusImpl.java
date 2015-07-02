package com.leo.dao.impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.leo.dao.NoStatus;
import com.leo.interceptor.HelloInterceptor;

@Stateless(mappedName = "NoStatusImpl")
@Interceptors({HelloInterceptor.class})
public class NoStatusImpl implements NoStatus {

	private int id = 0;
	@Override
	public String sayHello(String name) {
		System.out.println(id);
		id = id + 1;
		return name + "---date:" + new Date().toLocaleString();
	}

}
