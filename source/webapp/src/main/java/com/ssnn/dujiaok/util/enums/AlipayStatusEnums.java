package com.ssnn.dujiaok.util.enums;

import org.apache.commons.lang.StringUtils;

public enum AlipayStatusEnums {

	TRADE_UNPAY("TRADE_UNPAY",PayStatusEnums.UNPAID) ,
	TRADE_INIT("TRADE_INIT",PayStatusEnums.UNPAID),
	TRADE_SUCCESS("TRADE_SUCCESS" , PayStatusEnums.PAID) ,
	TRADE_FINISHED("TRADE_FINISHED" , PayStatusEnums.PAID),
	TRADE_OTHER("TRADE_OTHER"  , PayStatusEnums.UNKNOWN) ;
	
	private AlipayStatusEnums(String value , PayStatusEnums payStatus){
		this.payStatus = payStatus ;
		this.value = value ;
	}
	
	private PayStatusEnums payStatus ;
	private String value ;
	
	public PayStatusEnums getPayStatus() {
		return payStatus;
	}

	public String getValue() {
		return value;
	}

	public static PayStatusEnums toPayStatus(String alipayStatus) {
		String value = StringUtils.upperCase(alipayStatus) ;
		for(AlipayStatusEnums e : AlipayStatusEnums.values()){
			if(StringUtils.equals(e.getValue() , value)){
				return e.getPayStatus() ;
			}
		}
		return PayStatusEnums.UNKNOWN ;
	}
}
