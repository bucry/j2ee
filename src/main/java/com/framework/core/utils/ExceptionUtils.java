package com.framework.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author neo
 */
public final class ExceptionUtils {
    public static String stackTrace(Throwable e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    private ExceptionUtils() {
    }
}
