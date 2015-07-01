/**
 * FisrWsLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.oracle.ejb;

public class FisrWsLocator extends org.apache.axis.client.Service implements com.oracle.ejb.FisrWs {

    public FisrWsLocator() {
    }


    public FisrWsLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FisrWsLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for helloPort
    private java.lang.String helloPort_address = "http://localhost:7001/HelloWorldImpl/fisrWs";

    public java.lang.String gethelloPortAddress() {
        return helloPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String helloPortWSDDServiceName = "helloPort";

    public java.lang.String gethelloPortWSDDServiceName() {
        return helloPortWSDDServiceName;
    }

    public void sethelloPortWSDDServiceName(java.lang.String name) {
        helloPortWSDDServiceName = name;
    }

    public com.oracle.ejb.HelloWorldImplWeb gethelloPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(helloPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return gethelloPort(endpoint);
    }

    public com.oracle.ejb.HelloWorldImplWeb gethelloPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.oracle.ejb.HelloPortBindingStub _stub = new com.oracle.ejb.HelloPortBindingStub(portAddress, this);
            _stub.setPortName(gethelloPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void sethelloPortEndpointAddress(java.lang.String address) {
        helloPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.oracle.ejb.HelloWorldImplWeb.class.isAssignableFrom(serviceEndpointInterface)) {
                com.oracle.ejb.HelloPortBindingStub _stub = new com.oracle.ejb.HelloPortBindingStub(new java.net.URL(helloPort_address), this);
                _stub.setPortName(gethelloPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("helloPort".equals(inputPortName)) {
            return gethelloPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ejb.oracle.com", "fisrWs");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ejb.oracle.com", "helloPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("helloPort".equals(portName)) {
            sethelloPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
