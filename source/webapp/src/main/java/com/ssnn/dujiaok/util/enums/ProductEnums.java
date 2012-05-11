package com.ssnn.dujiaok.util.enums;

import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.constant.Constant;

/**
 * 产品Enum
 * @author shenjia.caosj 2012-1-17
 *
 */
public enum ProductEnums {
	
	UNKNOWN("" , "") ,
	/**
	 * 门票
	 */
	TICKET(Constant.PREFIX_TICKET , "打折门票") ,
	/**
	 * 酒店
	 */
	HOTEL(Constant.PREFIX_HOTEL , "酒店") ,
	/**
	 * 酒店房间
	 */
	HOTEL_ROOM(Constant.PREFIX_HOTELROOM , "房间") ,
	/**
	 * 自驾
	 */
	SELFDRIVE(Constant.PREFIX_SELFDRIVE , "周边自驾") ,
	
	
	
	
	;
	
	private String name ;
	private String desc ;
	private ProductEnums(String name , String desc){
		this.name = name ;
		this.desc = desc ;
	}
	
	public String getName(){
		return this.name ;
	}
	
	public String getDesc() {
		return desc;
	}

	public static ProductEnums fromValue(String name){
		ProductEnums[] enums = ProductEnums.values() ;
		for(ProductEnums e : enums){
			if(e.getName().equals(name)){
				return e ;
			}
		}
		return UNKNOWN ;
	}
	
	public static ProductEnums fromProductId(String productId){
		if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTEL)){
			return HOTEL ;
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTELROOM)){
			return HOTEL_ROOM ;
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_SELFDRIVE)){
			return SELFDRIVE ;
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_TICKET)){
			return TICKET ;
		}
		return UNKNOWN ;
	}
	
}
