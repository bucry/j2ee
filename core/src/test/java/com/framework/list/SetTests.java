package com.framework.list;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class SetTests {
	
	@Test
	public void setTest() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("");
		Set<String> set = new HashSet<String>();
		set.add("bfc");
		set.add("bfc");
		//System.out.println(set.size());
		
		Set<String> treeSet = new TreeSet<String>();
		treeSet.add("bfc");
		
		Map map = new HashMap();
		map.put(null, "bfc");
		map.put(null, "bfc");
		System.out.println(map.size());
		
		Map mapTable = new Hashtable();
		//mapTable.put(null, "bfc");
		mapTable.put("1", "bfc");
		System.out.println(mapTable.size());
		
	}

}
