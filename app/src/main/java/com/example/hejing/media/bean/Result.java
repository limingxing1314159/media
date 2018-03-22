package com.example.hejing.media.bean;


public class Result {
	private int code;
	private String msg;
	private String token;
	private Object data;

	public Result() {
	}

	public Result(int code, String msg, String token,Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.token = token;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getToken(){return token;}
	public void setToken(String token){this.token = token;}
	public Object getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", token=" + token + " , data=" + data + "]";
	}
}
