package com.ssnn.dujiaok.util.enums;

public enum StarRateEnums {

	_4(4 , "名宿/客栈") , 
	_5(5 , "农家乐") , 
	_6(6, "未评级酒店") , 
	_7(7,"准三星级酒店") , 
	_8(8,"三星级酒店") , 
	_9(9,"准四星级酒店") , 
	_10(10,"四星级酒店") ,
	_11(11,"准五星级酒店") ,
	_12(12,"五星级酒店"), ;
	
	private StarRateEnums(int value , String desc){
		this.value = value ;
		this.desc = desc ;
	}
	
	private int value ;
	
	private String desc ;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static StarRateEnums fromValue(int value){
		StarRateEnums[] enums = StarRateEnums.values() ;
		for(StarRateEnums e : enums){
			if(e.getValue() == value){
				return e ;
			}
		}
		return _6 ;
	}
	
}
