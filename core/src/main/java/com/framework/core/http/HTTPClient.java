package com.framework.core.http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.core.utils.StopWatch;
import com.framework.core.utils.TimeLength;

/**
 * @author neo
 */
public class HTTPClient {
    public static final TimeLength DEFAULT_TIME_OUT = TimeLength.minutes(2);
    public static final TimeLength NO_TIME_OUT = TimeLength.ZERO;

    private final Logger logger = LoggerFactory.getLogger(HTTPClient.class);

    DefaultHttpClient httpClient;
    final HttpParams params;

    boolean validateStatusCode = false;
    String basicAuthUser;
    String basicAuthPassword;

    boolean acceptSelfSignedCert = true;

    public HTTPClient() {
        params = new SyncBasicHttpParams();

        // set default time out
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, (int) DEFAULT_TIME_OUT.toMilliseconds());
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, (int) DEFAULT_TIME_OUT.toMilliseconds());

        DefaultHttpClient.setDefaultHttpParams(params);
    }

    public HTTPResponse execute(HTTPRequest request) {
        StopWatch watch = new StopWatch();
        try {
            HttpResponse response = executeMethod(request);

            String responseText = readResponseText(response);

            HTTPStatusCode statusCode = new HTTPStatusCode(response.getStatusLine().getStatusCode());
            validateStatusCode(statusCode);

            HTTPHeaders headers = HTTPHeaders.createResponseHeaders(response);
            return new HTTPResponse(statusCode, headers, responseText);
        } catch (IOException e) {
            throw new HTTPException(e);
        } finally {
            logger.debug(String.format("execute finished, elapsedTime=%d(ms)", watch.elapsedTime()));
        }
    }

    public HTTPBinaryResponse download(HTTPRequest request) {
        StopWatch watch = new StopWatch();
        try {
            HttpResponse response = executeMethod(request);

            byte[] responseContent = readResponseBytes(response);

            HTTPStatusCode statusCode = new HTTPStatusCode(response.getStatusLine().getStatusCode());
            validateStatusCode(statusCode);

            HTTPHeaders headers = HTTPHeaders.createResponseHeaders(response);
            return new HTTPBinaryResponse(statusCode, headers, responseContent);
        } catch (IOException e) {
            throw new HTTPException(e);
        } finally {
            logger.debug(String.format("download finished, elapsedTime=%d(ms)", watch.elapsedTime()));
        }
    }

    HttpResponse executeMethod(HTTPRequest request) throws IOException {
        HttpRequestBase httpRequest = request.createHTTPRequest();

        logger.debug("send request, url=" + httpRequest.getURI() + ", method=" + httpRequest.getMethod());
        request.logRequest();

        HttpResponse response = getOrCreateHTTPClient().execute(httpRequest);
        logger.debug("received response, statusCode=" + response.getStatusLine().getStatusCode());

        return response;
    }

    private String readResponseText(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity, HTTP.UTF_8);
        logger.debug("responseText=" + responseText);
        return responseText;
    }

    private byte[] readResponseBytes(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        byte[] responseContent = EntityUtils.toByteArray(entity);
        logger.debug("lengthOfResponseContent=" + responseContent.length);
        return responseContent;
    }

    synchronized DefaultHttpClient getOrCreateHTTPClient() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(), params);

            configureBasicAuth();

            try {
                configureHTTPS();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new HTTPException(e);
            }
        }
        return httpClient;
    }

    void validateStatusCode(HTTPStatusCode statusCode) {
        if (validateStatusCode) {
            if (statusCode.isSuccess()) return;
            if (statusCode.isRedirect()) return;
            throw new HTTPException("invalid response status code, statusCode=" + statusCode);
        }
    }

    private void configureBasicAuth() {
        if (basicAuthUser != null) {
            httpClient.getCredentialsProvider()
                    .setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(basicAuthUser, basicAuthPassword));
        }
    }

    private void configureHTTPS() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        TrustManager[] trustManagers = null;
        if (acceptSelfSignedCert) {
            trustManagers = new TrustManager[]{new SelfSignedX509TrustManager()};
        }

        SSLContext context = SSLContext.getInstance(SSLSocketFactory.TLS);
        context.init(null, trustManagers, null);

        X509HostnameVerifier hostnameVerifier = SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        if (acceptSelfSignedCert) {
            hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        }
        SSLSocketFactory socketFactory = new SSLSocketFactory(context, hostnameVerifier);

        Scheme scheme = new Scheme("https", 443, socketFactory);
        httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
    }

    public void setAcceptSelfSignedCert(boolean acceptSelfSignedCert) {
        this.acceptSelfSignedCert = acceptSelfSignedCert;
    }

    public void setBasicCredentials(String user, String password) {
        basicAuthUser = user;
        basicAuthPassword = password;
    }

    /**
     * set cookie policy
     *
     * @param cookiePolicy the cookie policy
     * @see org.apache.http.client.params.CookiePolicy
     */
    public void setCookiePolicy(String cookiePolicy) {
        params.setParameter(ClientPNames.COOKIE_POLICY, cookiePolicy);
    }

    public void setTimeOut(TimeLength timeOut) {
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, (int) timeOut.toMilliseconds());
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, (int) timeOut.toMilliseconds());
    }

    public void setHandleRedirect(boolean handleRedirect) {
        params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, handleRedirect);
    }

    public void setValidateStatusCode(boolean validateStatusCode) {
        this.validateStatusCode = validateStatusCode;
    }
}
