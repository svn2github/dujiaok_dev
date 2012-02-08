package com.ssnn.dujiaok.util.enums;

/**
 * 付款状态
 * 
 * @author shenjia.caosj 2012-2-2
 * 
 */
public enum PayStatusEnums {

	/**
	 * 
	 */
	UNKNOWN("", ""),
	
	/**
	 * 等待付款
	 */
	UNPAID("UNPAID", "未付款"),

	/**
	 * 已经付款
	 */
	PAID("PAID", "已付款"),

	 ;

	private String name;
	private String desc;

	private PayStatusEnums(String name, String desc) {
		this.desc = desc;
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public String getName() {
		return name;
	}

	public static PayStatusEnums fromValue(String name) {
		PayStatusEnums[] enums = PayStatusEnums.values();
		for (PayStatusEnums e : enums) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return UNKNOWN;
	}
}
