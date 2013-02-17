package com.ssnn.dujiaok.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * String 和 List 转换
 * @author langben 2012-1-13
 * 
 */
public class ArrayStringUtils {

	/**
	 * 将List转为String， ","分隔
	 * @param list
	 * @return
	 */
	public static String toString(List<String> list) {
		if(list == null || list.isEmpty()){
			return null ;
		}
		Iterator<String> i = list.iterator();
		StringBuilder sb = new StringBuilder();
		for (;;) {
			String e = i.next();
			e = StringUtils.trim(e) ;
			sb.append(e);
			if (!i.hasNext()){
				return sb.toString();
			}
			sb.append(", ");
		}
	}
	
	/**
	 * String转为List，“,”为分隔符
	 * @param listString
	 * @return
	 */
	public static List<String> toList(String listString){
		if(StringUtils.isBlank(listString)) ;
		String[] arr = StringUtils.split(listString,",") ;
		if(arr == null){
			return null ;
		}
		for(int i=0 ;i<arr.length;i++){
			arr[i] = StringUtils.trim(arr[i]) ;
		}
		return Arrays.asList(arr) ;
	}
}
