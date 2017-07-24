package com.mycloud.exception;

public class BaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseException(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	private Integer code;
	private String msg;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
