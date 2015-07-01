package com.miniweb.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.miniweb.utils.HTTPHeaders;

public class AnalysisHTTPHearder {

	private AnalysisHTTPHearder() {}
	private static Lock lock = new ReentrantLock();
	private static AnalysisHTTPHearder instance = null;
	
	public static AnalysisHTTPHearder getInstance() {
		try {
			if (instance == null) {
				lock.lock();
				if (instance == null) {
					instance = new AnalysisHTTPHearder();
				}
				lock.unlock();
			}
			return instance;
		} finally {
			
		}
	}
	
	public Map<String, String> analysisRequest(List<String> origin) {
		Map<String, String> headers = new HashMap<String, String>();
		
		for (String item : origin) {
			if (item.contains(HTTPHeaders.GET)) {
				String[] heads = item.split(" ");
				headers.put(HTTPHeaders.GET, heads[1]);
			}
		}
		
		return headers;
	}
}
