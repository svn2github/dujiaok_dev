package com.ssnn.dujiaok.web.action.model;

import org.apache.commons.lang.StringUtils;

/**
 * JsonModel
 * @param <T>
 */
public class JsonModel<T> {
	public static final String CODE_SUCCESS = "success";
	public static final String CODE_FAIL = "fail";
	public static final String CODE_AUTH_FAIL = "auth_fail";
	public static final String CODE_NO_DATA = "no_data";
	public static final String CODE_UNEXPECTED = "unexpected";
	public static final String CODE_ILL_ARGS = "ill_args";
	public static final String CODE_ALIPAY_ERR = "alipay_err" ;
	
	private String code;
	
	private String detail ;

	private T data;
	
	private String extraData ;
	
	public boolean isSuccess(){
		return StringUtils.equals(code,CODE_SUCCESS) ; 
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public void setSuccess(String code, T data) {
		setCode(code);
		setData(data);
	}
	
	public void setFail(String code, String detail) {
		setCode(code);
		setDetail(detail);
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}
}
