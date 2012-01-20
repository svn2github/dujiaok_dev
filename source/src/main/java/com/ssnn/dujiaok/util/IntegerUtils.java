package com.ssnn.dujiaok.util;

public class IntegerUtils {

	public static int toInt(Integer i){
		if( i == null ){
			return 0 ;
		}
		return i ;
	}
	
	public static int objectToInt(Object i){
		if(i == null){
			return 0 ;
		}
		if(i instanceof Integer){
			return toInt((Integer)i) ;
		}
		return Integer.parseInt(i.toString()) ;
	}
}
