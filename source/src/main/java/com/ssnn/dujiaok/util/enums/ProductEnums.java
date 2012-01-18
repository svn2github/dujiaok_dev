package com.ssnn.dujiaok.util.enums;

/**
 * 产品Enum
 * @author shenjia.caosj 2012-1-17
 *
 */
public enum ProductEnums {
	
	/**
	 * 门票
	 */
	TICKET("MP") ,
	/**
	 * 酒店
	 */
	HOTEL("JD") ,
	/**
	 * 酒店房间
	 */
	HOTEL_ROOM("FJ") ,
	/**
	 * 自驾
	 */
	SELFDRIVE("ZJ") ,
	
	UNKNOWN("UNKNOWN")
	
	
	;
	
	private String value ;
	private ProductEnums(String value){
		this.value = value ;
	}
	
	public String getValue(){
		return this.value ;
	}
	
	public static ProductEnums fromValue(String value){
		ProductEnums[] enums = ProductEnums.values() ;
		for(ProductEnums e : enums){
			if(e.getValue().equals(value)){
				return e ;
			}
		}
		return UNKNOWN ;
	}
}
