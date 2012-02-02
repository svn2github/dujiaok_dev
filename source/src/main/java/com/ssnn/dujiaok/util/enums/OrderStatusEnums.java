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
	CLOSED("CLOSED","关闭") ,
	
	/**
	 * 等待付款
	 */
	UNPAID("UNPAID","等待付款") ,
	
	/**
	 * 已经付款
	 */
	PAID("PAID","已经付款"),
	
	/**
	 * 成功
	 */
	SUCCESS("SUCCESS","成功") ,
	/**
	 * 
	 */
	UNKNOWN("UNKNOWN","状态异常") ,
	;
	
	
	private String value ;
	private String name ;
	
	private OrderStatusEnums(String value , String name){
		this.value = value ;
		this.name = name ;
	}
	
	public String getValue(){
		return this.value ;
	}
	
	public String getName() {
		return name;
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
