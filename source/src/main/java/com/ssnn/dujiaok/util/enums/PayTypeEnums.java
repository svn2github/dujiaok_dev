package com.ssnn.dujiaok.util.enums;

public enum PayTypeEnums {

	ONLINE("ONLINE","在线支付") , 
	CASH("CASH","前台现付") , 
	/**
	 * 
	 */
	UNKNOWN("UNKNOWN","状态异常") ,
	;
	
	
	private String value ;
	private String name ;
	
	private PayTypeEnums(String value , String name){
		this.value = value ;
		this.name = name ;
	}
	
	public String getValue(){
		return this.value ;
	}
	
	public String getName() {
		return name;
	}
	public static PayTypeEnums fromValue(String value){
		PayTypeEnums[] enums = PayTypeEnums.values() ;
		for(PayTypeEnums e : enums){
			if(e.getValue().equals(value)){
				return e ;
			}
		}
		return UNKNOWN ;
	}
	
}
