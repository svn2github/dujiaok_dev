package com.ssnn.dujiaok.util.enums;

/**
 * 订单状态
 * @author shenjia.caosj 2012-2-2
 *
 */
public enum OrderStatusEnums {

	/**
	 * 关闭
	 */
	CLOSED("CLOSED") ,
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
	private OrderStatusEnums(String value){
		this.value = value ;
	}
	
	public String getValue(){
		return this.value ;
	}
	
	public static OrderStatusEnums fromValue(String value){
		OrderStatusEnums[] enums = OrderStatusEnums.values() ;
		for(OrderStatusEnums e : enums){
			if(e.getValue().equals(value)){
				return e ;
			}
		}
		return UNKNOWN ;
	}
}
