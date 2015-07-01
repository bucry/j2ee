package com.oracle.webservice.bean;

import java.rmi.Remote;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface  LoginDao  extends Remote{
	@WebMethod
	public boolean isLogin(String name , String password);
}
