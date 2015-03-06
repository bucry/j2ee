package com.framework.core.platform.web.cache;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ResponseUtil {

    private static final int EMPTY_GZIPPED_CONTENT_SIZE = 20;

    private ResponseUtil() {
        
    }

    public static boolean shouldGzippedBodyBeZero(byte[] compressedBytes, HttpServletRequest request) {
        return compressedBytes.length == EMPTY_GZIPPED_CONTENT_SIZE;
    }

    public static boolean shouldBodyBeZero(HttpServletRequest request, int responseStatus) {
        return (responseStatus == HttpServletResponse.SC_NO_CONTENT) || (responseStatus == HttpServletResponse.SC_NOT_MODIFIED);
    }

    public static void addGzipHeader(final HttpServletResponse response) throws ResponseHeadersNotModifiableException {
        response.setHeader("Content-Encoding", "gzip");
        boolean containsEncoding = response.containsHeader("Content-Encoding");
        if (!containsEncoding) {
            throw new ResponseHeadersNotModifiableException("Failure when attempting to set Content-Encoding: gzip");
        }
    }

    public static void addVaryAcceptEncoding(final GenericResponseWrapper wrapper) {
        Collection<Header<? extends Serializable>> headers = wrapper.getAllHeaders();
        Header<? extends Serializable> varyHeader = null;
        for (Header<? extends Serializable> header : headers) {
            if ("Vary".equals(header.getName())) {
                varyHeader = header;
                break;
            }
        }
        if (varyHeader == null) {
            wrapper.setHeader("Vary", "Accept-Encoding");
        } else {
            String varyValue = varyHeader.getValue().toString();
            if (!"*".equals(varyValue) && !"Accept-Encoding".contains(varyValue)) {
                wrapper.setHeader("Vary", varyValue + ",Accept-Encoding");
            }
        }
    }
}