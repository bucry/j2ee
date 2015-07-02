package com.leo.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

import com.leo.dao.ShopService;
import com.leo.interceptor.HelloInterceptor;

@Stateful(mappedName="ShopService")
@Interceptors({HelloInterceptor.class})
public class ShopServiceImpl implements ShopService {

	
	private Map<String, Integer> buyInfo = new HashMap<String, Integer>();
	private int id = 0;
	
	@PostConstruct
	public void init() {
		System.out.println("ShopService init ...");
	}
	
	@PreDestroy
	public void onDestroy() {
		System.out.println("onDestroy..");
	}
	
	@Override
	public void addItem(String item) {
		System.out.println(id);
		id = id + 1;
		if (buyInfo.containsKey(item)) {
			buyInfo.put(item, buyInfo.get(item) + 1);
		} else {
			buyInfo.put(item, 1);
		}
	}

	@Override
	public Map<String, Integer> showDetail() {
		return buyInfo;
	}

}
