package com.ssnn.dujiaok.util.enums;

/**
 * 付款状态
 * @author shenjia.caosj 2012-2-2
 *
 */
public enum PayStatusEnums {

	/**
	 * 等待付款
	 */
	UNPAID("UNPAID") ,
	
	/**
	 * 已经付款
	 */
	PAID("PAID"),
	
	/**
	 * 
	 */
	UNKNOWN("UNKNOWN") ,
	;
	
	
	private String value ;
	private PayStatusEnums(String value){
		this.value = value ;
	}
	
	public String getValue(){
		return this.value ;
	}
	
	public static PayStatusEnums fromValue(String value){
		PayStatusEnums[] enums = PayStatusEnums.values() ;
		for(PayStatusEnums e : enums){
			if(e.getValue().equals(value)){
				return e ;
			}
		}
		return UNKNOWN ;
	}
}
