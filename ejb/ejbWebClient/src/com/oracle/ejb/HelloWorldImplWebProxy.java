package com.oracle.ejb;

public class HelloWorldImplWebProxy implements com.oracle.ejb.HelloWorldImplWeb {
  private String _endpoint = null;
  private com.oracle.ejb.HelloWorldImplWeb helloWorldImplWeb = null;
  
  public HelloWorldImplWebProxy() {
    _initHelloWorldImplWebProxy();
  }
  
  public HelloWorldImplWebProxy(String endpoint) {
    _endpoint = endpoint;
    _initHelloWorldImplWebProxy();
  }
  
  private void _initHelloWorldImplWebProxy() {
    try {
      helloWorldImplWeb = (new com.oracle.ejb.FisrWsLocator()).gethelloPort();
      if (helloWorldImplWeb != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)helloWorldImplWeb)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)helloWorldImplWeb)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (helloWorldImplWeb != null)
      ((javax.xml.rpc.Stub)helloWorldImplWeb)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.oracle.ejb.HelloWorldImplWeb getHelloWorldImplWeb() {
    if (helloWorldImplWeb == null)
      _initHelloWorldImplWebProxy();
    return helloWorldImplWeb;
  }
  
  public java.lang.String sayHello(java.lang.String arg0) throws java.rmi.RemoteException{
    if (helloWorldImplWeb == null)
      _initHelloWorldImplWebProxy();
    return helloWorldImplWeb.sayHello(arg0);
  }
  
  
}