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
	UNPAID("UNPAID","未付款") ,
	
	/**
	 * 已经付款
	 */
	PAID("PAID","已付款"),
	
	
	
	/**
	 * 
	 */
	UNKNOWN("UNKNOWN","状态异常") ,
	;
	
	
	private String value ;
	private String name ;
	
	private PayStatusEnums(String value , String name){
		this.value = value ;
		this.name = name ;
	}
	
	public String getValue(){
		return this.value ;
	}
	
	public String getName() {
		return name;
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
