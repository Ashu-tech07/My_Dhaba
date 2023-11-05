package com.mydhaba;

public class CustomResponse {

	String message;
	Object data;
	
	
	@Override
	public String toString() {
		return "CustomResponse [message=" + message + ", data=" + data + "]";
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public CustomResponse(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}
	public CustomResponse() {
		super();
	}
	
	
	
	
}
