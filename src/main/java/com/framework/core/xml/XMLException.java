package com.framework.core.xml;


/**
 * @author neo
 */
public final class XMLException extends RuntimeException {
   
	private static final long serialVersionUID = -8705700009324107946L;

	public XMLException(String message) {
        super(message);
    }

    public XMLException(Throwable cause) {
        super(cause);
    }
}
