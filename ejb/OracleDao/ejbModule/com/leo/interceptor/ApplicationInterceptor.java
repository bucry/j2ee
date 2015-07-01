package com.leo.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class ApplicationInterceptor {

	@AroundInvoke
	public Object log(InvocationContext ctx) throws Exception {
		Object rvt = ctx.proceed();
		System.out.println("-------");
		if (rvt != null) {
			System.out.println("+++++++++");
		}
		return rvt;
	}
}
