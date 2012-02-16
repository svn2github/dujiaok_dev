package com.ssnn.dujiaok.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.util.enums.ProductEnums;

/**
 * 
 * @author shenjia.caosj 2012-1-17
 *
 */
public class UniqueIDUtil {

	private static final String format = "yyMMddHHmmssS" ;
	
	private static long tickcount = 0 ;
	
	public static String buildUniqueId(ProductEnums product){
		String time = getTimestamp() ;
		String random = String.valueOf(new Random().nextInt(10)) ;
		return product.getName() + time + random ; 
	}
	
	public static String buildOrderId(MemberDO member){
		DateFormat df = new SimpleDateFormat(format) ;
		String dd = df.format(new Date()) ;
		long l = Long.valueOf(dd) ;
		String time = Long.toString(l, Character.MAX_RADIX) ;
		time = StringUtils.upperCase(time) ;
		return time + member.getId() ;
	}
	
	private static String getTimestamp(){
		Date d = new Date() ;
		return new SimpleDateFormat(format).format(d) ;
	}
	
	private static String conventE2N(String memberId){
		int hash = memberId.hashCode() ;
		return String.valueOf(Math.abs(hash)) ;
	}
	
	public static void main(String[] args) {
		
		
	}
}
