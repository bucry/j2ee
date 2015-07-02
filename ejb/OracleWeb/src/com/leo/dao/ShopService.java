package com.leo.dao;

import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface ShopService {
	public void addItem(String item);
	public Map<String, Integer> showDetail();
}
