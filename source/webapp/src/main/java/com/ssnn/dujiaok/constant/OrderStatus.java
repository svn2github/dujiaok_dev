package com.ssnn.dujiaok.constant;

public enum OrderStatus {
	UNPAY("未付款"),
	SUCCESS("已付款"),
	FAILED("付款失败"),
	CLOSED("已关闭"),
	CONFIRM("已确认"),
	DRAWBACKING("退款中"),
	DRAWBACKED("已退款");
	
	/**
	 * 
	 */
	private String desc;
	
	/**
	 * 
	 * @param desc
	 */
	private OrderStatus(String desc) {
		this.desc = desc;
	}
	
	/**
	 * 状态描述.
	 * @return 状态描述.
	 */
	public String getDesc() {
		return this.desc;
	}
}
