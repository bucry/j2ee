package com.framework.core.platform.web.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageInfo implements Serializable {
    
    private static final Logger LOG = LoggerFactory.getLogger(PageInfo.class);
    
    private final List<Header<? extends Serializable>> responseHeaders = new ArrayList<Header<? extends Serializable>>();
    
    private static final int FOUR_KB = 4196;
    
    private static final int GZIP_MAGIC_NUMBER_BYTE_1 = 31;
    
    private static final int GZIP_MAGIC_NUMBER_BYTE_2 = -117;

    private String contentType;
    
    private byte[] gzippedBody;
    
    private byte[] ungzippedBody;
    
    private int statusCode;
    
    private boolean storeGzipped;
    
    private Date created;
    
    public PageInfo(final int statusCode, final String contentType, final byte[] body, boolean storeGzipped,  final Collection<Header<? extends Serializable>> headers) throws AlreadyGzippedException {
        this.init(statusCode, contentType, headers,  body, storeGzipped);
    }

    private void init(final int statusCode, final String contentType, final Collection<Header<? extends Serializable>> headers,  final byte[] body, boolean storeGzipped) throws AlreadyGzippedException {
        if (headers != null) this.responseHeaders.addAll(headers);
        created = new Date();
        this.contentType = contentType;
        this.storeGzipped = storeGzipped;
        this.statusCode = statusCode;
        
        try {
            if (storeGzipped) {
                // gunzip on demand
                ungzippedBody = null;
                if (isBodyParameterGzipped()) {
                    gzippedBody = body;
                } else {
                    gzippedBody = gzip(body);
                }
            } else {
                if (isBodyParameterGzipped()) {
                    throw new IllegalArgumentException("Non gzip content has been gzipped.");
                } else {
                    ungzippedBody = body;
                }
            }
        } catch (IOException e) {
            LOG.error("Error ungzipping gzipped body", e);
        }
    }

    

    private byte[] gzip(byte[] ungzipped) throws IOException, AlreadyGzippedException {
        if (isGzipped(ungzipped)) {
            throw new AlreadyGzippedException("The byte[] is already gzipped. It should not be gzipped again.");
        }
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bytes);
        gzipOutputStream.write(ungzipped);
        gzipOutputStream.close();
        return bytes.toByteArray();
    }

    private boolean isBodyParameterGzipped() {
        for (final Header<? extends Serializable> header : this.responseHeaders) {
            if ("gzip".equals(header.getValue())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGzipped(byte[] candidate) {
        if (candidate == null || candidate.length < 2) {
            return false;
        } else {
            return candidate[0] == GZIP_MAGIC_NUMBER_BYTE_1 && candidate[1] == GZIP_MAGIC_NUMBER_BYTE_2;
        }
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getGzippedBody() {
        if (storeGzipped) {
            return gzippedBody;
        } else {
            return null;
        }
    }

    public List<Header<? extends Serializable>> getHeaders() {
        return this.responseHeaders;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public byte[] getUngzippedBody() throws IOException {
        if (storeGzipped) {
            return ungzip(gzippedBody);
        } else {
            return ungzippedBody;
        }
    }

    private byte[] ungzip(final byte[] gzipped) throws IOException {
        final GZIPInputStream inputStream = new GZIPInputStream(
                new ByteArrayInputStream(gzipped));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                gzipped.length);
        final byte[] buffer = new byte[FOUR_KB];
        int bytesRead = 0;
        while (bytesRead != -1) {
            bytesRead = inputStream.read(buffer, 0, FOUR_KB);
            if (bytesRead != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        }
        byte[] ungzipped = byteArrayOutputStream.toByteArray();
        inputStream.close();
        byteArrayOutputStream.close();
        return ungzipped;
    }

    public boolean hasGzippedBody() {
        return gzippedBody != null;
    }

    public boolean hasUngzippedBody() {
        return ungzippedBody != null;
    }

    public boolean isOk() {
        return statusCode == HttpServletResponse.SC_OK;
    }

    public Date getCreated() {
        return created;
    }

    public boolean isStoreGzipped() {
        return storeGzipped;
    }

    public void setStoreGzipped(boolean storeGzipped) {
        this.storeGzipped = storeGzipped;
    }

    public List<Header<? extends Serializable>> getResponseHeaders() {
        return responseHeaders;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setGzippedBody(byte[] gzippedBody) {
        this.gzippedBody = gzippedBody;
    }

    public void setUngzippedBody(byte[] ungzippedBody) {
        this.ungzippedBody = ungzippedBody;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}