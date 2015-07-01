package com.oracle.test;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.oracle.ejb.FisrWs;
import com.oracle.ejb.FisrWsLocator;
import com.oracle.ejb.HelloWorldImplWeb;


public class TestWbServiceClient {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws ServiceException 
	 */
	public static void main(String[] args) throws RemoteException, ServiceException {
		FisrWs helloService=new FisrWsLocator();
		HelloWorldImplWeb hello=helloService.gethelloPort();
		//System.out.println(helloService.getHelloPort().sayHello("ss"));
		System.out.println(hello.sayHello("bfc"));
	}

}
