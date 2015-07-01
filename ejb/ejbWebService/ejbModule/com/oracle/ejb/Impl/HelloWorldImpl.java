package com.oracle.ejb.Impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.oracle.ejb.HelloWorld;

@Stateless(mappedName = "HelloWorldImpl")
@Remote(HelloWorld.class)

@WebService(name="HelloWorldImplWeb"
			, serviceName="fisrWs"
			, portName="helloPort"
			, targetNamespace="http://ejb.oracle.com")
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String sayHello(String name) {
		return "Hello:"+name;
	}

}
