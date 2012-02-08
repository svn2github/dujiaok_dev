package com.ssnn.dujiaok.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.ssnn.dujiaok.util.enums.ProductEnums;

/**
 * 
 * @author shenjia.caosj 2012-1-17
 *
 */
public class UniqueIDUtil {

	private static final String format = "yyMMddHHmmssS" ;
	
	public static String getUniqueID(ProductEnums product){
		String time = getTimestamp() ;
		String random = String.valueOf(new Random().nextInt(10)) ;
		return product.getName() + time + random ; 
	}
	
	public static String getOrderID(String memberId){
		String time = getTimestamp() ;
		String random = String.valueOf(new Random().nextInt(100)) ;
		String m = conventE2N(memberId) ;
		return m + random + time ;
	}
	
	private static String getTimestamp(){
		Date d = new Date() ;
		return new SimpleDateFormat(format).format(d) ;
	}
	
	private static String conventE2N(String memberId){
		int hash = memberId.hashCode() ;
		return String.valueOf(Math.abs(hash)) ;
	}
}
