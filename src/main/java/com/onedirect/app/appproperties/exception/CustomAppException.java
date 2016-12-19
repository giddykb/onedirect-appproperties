package com.onedirect.app.appproperties.exception;

public class CustomAppException extends RuntimeException{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2409497474275505699L;

	
	private String errCode;
	private String errMsg;
	
	public CustomAppException(String errCode,String errMsg) {
		// TODO Auto-generated constructor stub
		this.errCode=errCode;
		this.errMsg=errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	@Override
	public String toString() {
		return "CustomAppException [errCode=" + errCode + ", errMsg=" + errMsg + "]";
	}
}
