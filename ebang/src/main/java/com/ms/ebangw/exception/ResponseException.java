package com.ms.ebangw.exception;

/**
 * 响应数据含error状态，抛出此异常
 * @author chenwenbiao
 *
 */
public class ResponseException extends Exception{

	/**
	 * 错误码
	 */
	private String code = null;
	
	/**
	 * 错误信息
	 */
	private String message = null;

	public ResponseException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
