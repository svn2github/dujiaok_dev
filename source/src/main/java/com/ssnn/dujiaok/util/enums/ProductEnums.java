package com.ssnn.dujiaok.util.enums;

import com.ssnn.dujiaok.constant.Constant;

/**
 * 产品Enum
 * @author shenjia.caosj 2012-1-17
 *
 */
public enum ProductEnums {
	
	/**
	 * 门票
	 */
	TICKET(Constant.PREFIX_TICKET) ,
	/**
	 * 酒店
	 */
	HOTEL(Constant.PREFIX_HOTEL) ,
	/**
	 * 酒店房间
	 */
	HOTEL_ROOM(Constant.PREFIX_HOTELROOM) ,
	/**
	 * 自驾
	 */
	SELFDRIVE(Constant.PREFIX_SELFDRIVE) ,
	
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
