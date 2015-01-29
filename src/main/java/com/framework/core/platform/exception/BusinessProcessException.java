package com.framework.core.platform.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author qinyu
 */
public class BusinessProcessException extends RuntimeException {
    public BusinessProcessException(String message) {
        super(message);
    }

    public BusinessProcessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static String getStackTraceMessage(Throwable e) throws IOException  {
        String exceptionMessage = null;
        StringWriter stringWriter = null;
        PrintWriter printWriter = null;
        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            stringWriter.flush();
            exceptionMessage = "\n" + stringWriter.toString();
        } finally {
            if (null != printWriter)  printWriter.close();
            if (null != stringWriter) stringWriter.close();
        }
        return exceptionMessage;
    }
}