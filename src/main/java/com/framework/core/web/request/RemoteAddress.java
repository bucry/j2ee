package com.framework.core.web.request;

import javax.servlet.http.HttpServletRequest;

import com.framework.core.utils.ReadOnly;
import com.framework.core.utils.StringUtils;

public class RemoteAddress {
    
    private static final String HTTP_HEADER_X_FORWARDED_FOR = "x-forwarded-for";

    public static RemoteAddress create(HttpServletRequest request) {
        String directRemoteAddress = request.getRemoteAddr();
        String xForwardedFor = request.getHeader(HTTP_HEADER_X_FORWARDED_FOR);
        RemoteAddress remoteAddress = new RemoteAddress();
        remoteAddress.setRemoteAddress(directRemoteAddress);
        remoteAddress.setXForwardedFor(xForwardedFor);
        return remoteAddress;
    }

    private final ReadOnly<String> remoteAddress = new ReadOnly<String>();
    // for original ip if there is proxy
    private final ReadOnly<String> xForwardedFor = new ReadOnly<String>();

    public String getRemoteAddress() {
        return remoteAddress.value();
    }

    public String getXForwardedFor() {
        return xForwardedFor.value();
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress.set(remoteAddress);
    }

    public void setXForwardedFor(String xForwardedFor) {
        this.xForwardedFor.set(xForwardedFor);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.hasText(xForwardedFor.value())) {
            builder.append(xForwardedFor).append(", ");
        }
        builder.append(remoteAddress);
        return builder.toString();
    }
}
