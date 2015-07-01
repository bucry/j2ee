package com.oracle.interceptor.session;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.oracle.interceptor.MyInterceptor;

@Stateless(mappedName="HelloBfc")
@Interceptors(MyInterceptor.class)
public class Session implements SessionDao{

	public void sayOne(String name) {
		System.out.println("this is one :" + name);
	}
	
	public void sayTwo(String name) {
		System.out.println("this is two :" + name);
	}

}
