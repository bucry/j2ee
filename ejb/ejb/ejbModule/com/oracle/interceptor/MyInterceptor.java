package com.oracle.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;



public class MyInterceptor {
	@AroundInvoke
	public Object log(InvocationContext ctx) throws Exception{
		System.out.println("------begin------");
		Object rvt = ctx.proceed();
		if(rvt != null){
			rvt = "À¹½ØÆ÷·µ»ØµÄÖµ:" + rvt;
		}
		System.out.println("----end-----");
		return rvt;
	}
}
