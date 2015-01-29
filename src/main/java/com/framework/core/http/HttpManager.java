package com.framework.core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.util.StringUtils;

public class HttpManager {

	/**
	 * @Description : 自定义POST请求
	 * @param url : 
	 * @param parm
	 */
	public static void post(String url, String parm) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost method = new HttpPost(url);
		try {
			httpclient.execute(method);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description : 创建GET请求
	 * @param url : GET请求链接
	 * @return : 对象json化数据
	 */
	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet method = new HttpGet(url);
		StringBuilder content = new StringBuilder();
		try {
			HttpResponse response = httpclient.execute(method);
            HttpEntity entity = response.getEntity();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            String line = null;  
            while ((line = reader.readLine()) != null) {
            	content.append(line);
            }  
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return StringUtils.hasText(content.toString()) ? content.toString() : null;
	}

}
