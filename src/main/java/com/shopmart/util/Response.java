package com.shopmart.util;

public class Response {
	private int status;
	private String message;
	private Object content;
	
	public Response() {
	}
	public Response(int status, String message, Object content) {
		this.status = status;
		this.message = message;
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
}