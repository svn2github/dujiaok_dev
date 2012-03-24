package com.ssnn.dujiaok.util.enums;

public enum AddProductEnums {

	/**
	 * 
	 */
	INSURANCE("INSURANCE","保险") ,
	
	UNKNOWN("","") ;
	;
	
	
	private String desc ;
	private String name ;
	
	private AddProductEnums(String name , String desc){
		this.desc = desc ;
		this.name = name ;
	}
	
	public String getDesc(){
		return this.desc ;
	}
	
	public String getName() {
		return name;
	}
	public static AddProductEnums fromValue(String name){
		AddProductEnums[] enums = AddProductEnums.values() ;
		for(AddProductEnums e : enums){
			if(e.getName().equals(name)){
				return e ;
			}
		}
		return UNKNOWN ;
	}
}
