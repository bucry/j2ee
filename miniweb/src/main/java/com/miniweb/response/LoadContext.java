package com.miniweb.response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.miniweb.utils.HTTPHeaders;
import com.miniweb.utils.HTTPStatus;

public class LoadContext {
	
	private Map<String, String> cache = new ConcurrentHashMap<String, String>();
	
	private LoadContext() {}
	
	private static LoadContext instance = null;
	
	private static Lock lock = new ReentrantLock();
	
	public static LoadContext getInstance() {
		try {
			if (instance == null) {
				lock.lock();
				if (instance == null) {
					instance = new LoadContext();
				}
				lock.unlock();
			}
			return instance;
		} finally {
			
		}
	}
	
	private List<Header> createHeaders() {
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("Content-Type", " text/html"));
		headers.add(new Header("charset", " utf-8"));

		return headers;
	}
	
	public String getContext(Map<String, String> headers) {
		String link = headers.get(HTTPHeaders.GET);
		
		if (link == null || "".equals(link) || link.length() == 0 || "/".equals(link)) {
			link = "/index.html";
		}
		
		String context;
		if (cache.containsKey(link)) {
			context = cache.get(link);
		} else {
			context = createResponseContent(readFileByLines("webRoot" + link), createHeaders());
			cache.put(link, context);
		}
		return context;
	}

	
	private String createResponseContent(Response response, List<Header> headers) {
		switch (response.getStatusCode()) {
		case HTTPStatus.NOT_FOUND_CODE:
			headers.add(new Header("Content-Length", String.valueOf(HTTPStatus.NOT_FOUND.length())));
			return new HTTPResponse().addStatus(HTTPStatus.NOT_FOUND).addHeaders(headers).addContent(HTTPStatus.NOT_FOUND).getContent();
		case HTTPStatus.OK_CODE:
			headers.add(new Header("Content-Length", String.valueOf(response.getContent().length())));
			return new HTTPResponse().addStatus(HTTPStatus.OK).addHeaders(headers).addContent(response.getContent()).getContent();
		case HTTPStatus.INTERNAL_SERVER_ERROR_CODE:
			headers.add(new Header("Content-Length", String.valueOf(HTTPStatus.INTERNAL_SERVER_ERROR.length())));
			return new HTTPResponse().addStatus(HTTPStatus.INTERNAL_SERVER_ERROR).addHeaders(headers).addContent(HTTPStatus.INTERNAL_SERVER_ERROR).getContent();
		default:
			headers.add(new Header("Content-Length", String.valueOf(HTTPStatus.NOT_FOUND.length())));
			return new HTTPResponse().addStatus(HTTPStatus.NOT_FOUND).addHeaders(headers).addContent(HTTPStatus.NOT_FOUND).getContent();
		}
	}
	
	
	private Response readFileByLines(String fileName) {
		Response response = new Response();
		StringBuilder sb = new StringBuilder();
        File file = new File(fileName);
        if (!file.exists()) {
        	response.setStatusCode(HTTPStatus.NOT_FOUND_CODE);
        	return response;
        }
        
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
            response.setContent(sb.toString());
            response.setStatusCode(HTTPStatus.OK_CODE);
        } catch (IOException e) {
        	response.setStatusCode(HTTPStatus.INTERNAL_SERVER_ERROR_CODE);
            e.printStackTrace();
            
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        
        return response;
    }
}
