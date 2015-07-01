package com.miniweb.utils;

public class HTTPStatus {

	
	public static final String FILE_NOTFOUND = "HTTP/1.1 404 File Not Found\r\n" +   
            								   "Content-Type: text/html\r\n" +   
            								   "Content-Length: 23\r\n" +        
            								   "\r\n" +   "<h1>File Not Found</h1>";  
	
	
	
	
    public static final String CONTINUE = "100 Continue";
    public static final String SWITCHING_PROTOCOL = "101 Switching Protocol";
    public static final String PROCESSING = "102 Processing";

    public static final String OK = "200 OK";
    public static final int OK_CODE = 200;
    
    public static final String CREATE = "201 Created";
    public static final String ACCEPTED = "202 Accepted";
    public static final String NON_AUTHORATIVE = "203 Non-Authoritative Information";
    public static final String NO_CONTENT = "204 No Content";
    public static final String RESET_CONTENT = "205 Reset Content";
    public static final String PARTIAL_CONTENT = "206 Partial Content";
    public static final String MULTI_STATU = "207 Multi-Statu";

    public static final String MULTIPLE_CHPICE = "300 Multiple Choice";
    public static final String MOVED = "301 Moved Permanently";
    public static final String FOUND = "302 Found";
    public static final String SEE_OTHER = "303 See Other";
    public static final String NOT_MODIFIED = "304 Not Modified";
    public static final String USE_PROY = "305 Use Proxy";
    public static final String SWITCH_PROXY = "306 Switch Proxy";
    public static final String REDIRECT = "307 Temporary Redirect";


    public static final String BAD_REQUEST = "400 Bad Request";
    public static final String UNAUTHORIZED = "401 Unauthorized";
    public static final String PAYMENT_REQUIRED = "402 Payment Required";
    public static final String FORBIDDEN = "403 Forbidden";
    public static final int FORBIDDEN_CODE = 403;
    
    public static final String NOT_FOUND = "404 Not Found";
    public static final int NOT_FOUND_CODE = 404;
    
    public static final String METHOD_NOT_ALLOWED = "405 Method Not Allowed";
    public static final String NOT_ACCEPTABLE = "406 Not Acceptable";
    public static final String PROXY_AUTHENTICATION_REQUIRED = "407 Proxy Authentication Required";
    public static final String REQUEST_TIMEOUT = "408 Request Timeout";
    public static final String CONFLICT = "409 Conflict";
    public static final String GONE = "410 Gone";
    public static final String LENGTH_REQUIRED = "411 Length Required";
    public static final String PRECONDITION_FAILED = "412 Precondition Failed";
    public static final String REQUEST_ENTITY_TOO_LAGRE = "413 Request Entity Too Large";
    public static final String REQUEST_URI_TOO_LONG = "414 Request-URI Too Long";
    public static final String UNSUPPORTED = "415 Unsupported Media Type";
    public static final String NOT_SATISIFIABLE = "416 Requested Range Not Satisfiable";
    public static final String EXCEPTION_FAILED = "417 Expectation Failed";
    public static final String UNPROCESSABLE = "422 Unprocessable Entity";
    public static final String DEPENDENCY = "424 Failed Dependency";
    public static final String COLLECTION = "425 Unordered Collection";
    public static final String UPGRADE_REQUIRED = "426 Upgrade Required";
    public static final String RETRY_WITH = "449 Retry With";


    public static final String INTERNAL_SERVER_ERROR = "500 Internal Server Error";
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;
    
    public static final String NOT_IMPLEMENTED = "501 Not Implemented";
    public static final String BAD_GETWARY = "502 Bad Gateway";
    public static final int BAD_GETWARY_CODE = 502;
    
    public static final String SERVER_UNAVAILABLE = "503 Service Unavailable";
    public static final int SERVER_UNAVAILABLE_CODE = 503;
    
    public static final String GATEWAY_TIMEOUT = "504 Gateway Timeout";
    public static final int GATEWAY_TIMEOUT_CODE = 504;
    
    public static final String HTTP_VERSION_NOT_SUPPORTED = "505 HTTP Version Not Supported";
    public static final String VARIANT_ALSO_NEGOTIATE = "506 Variant Also Negotiate";
    public static final String INSUFFICIENT = "507 Insufficient Storage";
    public static final String LIMIT = "509 Bandwidth Limit Exceeded";
    public static final String NOT_EXTENDED = "510 Not Extended";
}
