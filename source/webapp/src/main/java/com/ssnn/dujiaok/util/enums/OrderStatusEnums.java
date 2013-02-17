package com.ssnn.dujiaok.util.enums;

/**
 * 订单状态
 * @author langben 2012-2-2
 *
 */
public enum OrderStatusEnums {

	/**
	 * 
	 */
	UNKNOWN("","") ,
	
	
	/**
	 * 等待付款
	 */
	UNPAID("UNPAID","等待付款") ,
	
	/**
	 * 等待预订确认
	 */
	CONFIRM("CONFIRM","等待预订确认"),
	
	/**
	 * 成功
	 */
	SUCCESS("SUCCESS","预订成功") ,
	
	/**
	 * 关闭
	 */
	CLOSED("CLOSED","订单关闭") ,

	/**
	 * 退款中
	 */
	DRAWBACKING("DRAWBACKING" , "退款中，订单关闭") ,
	
	/**
	 * 退款
	 */
	DRAWBACK("DRAWBACK" ,"已退款，订单关闭") , 
	;
	
	
	private String desc ;
	private String name ;
	
	private OrderStatusEnums(String name , String desc){
		this.desc = desc ;
		this.name = name ;
	}
	
	public String getDesc(){
		return this.desc ;
	}
	
	public String getName() {
		return name;
	}
	public static OrderStatusEnums fromValue(String name){
		OrderStatusEnums[] enums = OrderStatusEnums.values() ;
		for(OrderStatusEnums e : enums){
			if(e.getName().equals(name)){
				return e ;
			}
		}
		return UNKNOWN ;
	}
}
