package com.ssnn.dujiaok.util.enums;

public enum PayTypeEnums {

	/**
	 * 
	 */
	UNKNOWN("","") ,
	
	ONLINE("ONLINE","在线支付") , 
	
	CASH("CASH","前台现付") , 
	
	;
	
	
	private String desc ;
	private String name ;
	
	private PayTypeEnums(String name , String desc){
		this.desc = desc ;
		this.name = name ;
	}
	
	public String getDesc(){
		return this.desc ;
	}
	
	public String getName() {
		return name;
	}
	public static PayTypeEnums fromValue(String name){
		PayTypeEnums[] enums = PayTypeEnums.values() ;
		for(PayTypeEnums e : enums){
			if(e.getName().equals(name)){
				return e ;
			}
		}
		return UNKNOWN ;
	}
	
}
