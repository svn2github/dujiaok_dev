package com.ssnn.dujiaok.util.string;

public final class StringUtil {
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	public static String concat(Object ...objs) {
		if (objs == null) {
			return "";
		} else {
			StringBuilder result = new StringBuilder();
			for(Object info : objs) {
				if (info != null) {
					result.append(info);
				}
			}
			return result.toString();
		}
	}
}
