/**
 * FisrWs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.oracle.ejb;

public interface FisrWs extends javax.xml.rpc.Service {
    public java.lang.String gethelloPortAddress();

    public com.oracle.ejb.HelloWorldImplWeb gethelloPort() throws javax.xml.rpc.ServiceException;

    public com.oracle.ejb.HelloWorldImplWeb gethelloPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
