package com.leo.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class HelloInterceptor {
	  //使用@AroundInvoke注释，指定了要做拦截器的方法。
	  //使用此注释的格式必须遵守public Object XXX(InvocationContext ctx)throws Exception
	  @AroundInvoke
	  public Object log(InvocationContext ctx) throws Exception{
	    System.out.println("interceptor.....");
	    System.out.println("此时执行的方法："+ctx.getMethod().getName());
	    Object obj=ctx.proceed();
	    System.out.println(ctx.getMethod().getName()+"已经执行成功。");
	    return obj;
	  }

	}