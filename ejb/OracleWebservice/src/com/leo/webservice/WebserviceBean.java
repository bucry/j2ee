package com.leo.webservice;

import javax.ejb.Stateless;
import javax.jws.WebService;

import com.leo.webservice.impl.IWebserviceBean;

@Stateless
@WebService(name="webserviceBean", serviceName="firstWebserviceBean", 
portName="webserviceBeanPort", targetNamespace="http://www.bucry.com")
public class WebserviceBean implements IWebserviceBean {
	
	@Override
	public String sayName(String name) {
		return name;
	}

}
