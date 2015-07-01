package com.miniweb.response;

import java.util.List;

public class HTTPResponse {

	private StringBuilder content = new StringBuilder();
	
	public HTTPResponse addStatus(String status) {
		content.append("HTTP/1.1 ").append(status).append("\r\n");
		return this;
	}
	
	public HTTPResponse addHeaders(List<Header> headers) {
		for (Header header : headers) {
			content.append(header.getKey()).append(":").append(header.getValue()).append("\r\n");
		}
		return this;
	}
	
	public HTTPResponse addContent(String html) {
		content.append("\r\n").append(html);
		return this;
	}
	
	public String getContent() {
		return content.toString();
	}
}
