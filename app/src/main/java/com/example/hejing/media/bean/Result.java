package com.example.hejing.media.bean;

import java.io.Serializable;

public class Result implements Serializable {
	private int code = -1;
	private boolean msg;
	private Object data;
	public Result() {
	}
	public Result(boolean msg, int code){
		this.msg = msg;
		this.code = code;
	}
	public Result(int code, boolean msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean isMsg() {
		return msg;
	}
	public void setMsg(boolean msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
}
