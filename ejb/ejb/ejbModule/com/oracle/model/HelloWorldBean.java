package com.oracle.model;

import javax.ejb.Remote;
import javax.ejb.Stateless;


@Stateless(name="HelloWorldBean",mappedName = "HelloWorldBean")
@Remote(IHelloWorld.class)
public class HelloWorldBean implements IHelloWorld {

	private static final long serialVersionUID = 3339674961619709264L;

	public String sayHello(String name) {
		return name+" hello,this is my first EJB";
	}

}
