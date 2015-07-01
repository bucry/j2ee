package com.oracle.test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;

public class WebServiceTest {

	public static void main(String[] args) {
		String userName = "bfc";
		String password = "123456";
		try {
			URL url = new URL("http://localhost:7001/HelloWorldImpl/fisrWs?WSDL");
			QName qName = new QName("http://ejb.oracle.com/HelloWorldImplWeb/","helo");
			ServiceFactory factory = ServiceFactory.newInstance();
			Service service = factory.createService(url, qName);
			//HelloWorld h = service.getPort(HelloWorldImplWeb.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
