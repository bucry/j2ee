package com.framework.entity;

import org.junit.Test;

public class HashCodeTest {

	@Test
	public void eques() {
		Entity e1 = new Entity();
		e1.setName("bfc");
		Entity e2 = new Entity();
		e2.setName("bfc");
		System.out.println(e1 == e2);
		System.out.println(e1.equals(e2));
	}
}
