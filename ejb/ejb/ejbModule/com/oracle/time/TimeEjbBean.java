package com.oracle.time;

import java.sql.Time;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;


@Stateless(mappedName = "TimeEjb")
public class TimeEjbBean implements TimeEjb {

	//定义设置定时器的方法
	@Resource
	TimerService timeService;
	
	@Override
	public void setTime(Date init, long interval) {
		//启动一个每隔interval时间就会启动的定时器
		timeService.createTimer(init, interval,"新的定时器");
	}

	//定义定时执行的方法
	@Timeout
	public void check(Time timer) {
		System.out.println("定时器信息: " + ((Timer) timer).getInfo());
		System.out.println("模拟系统检查");
	}

}
