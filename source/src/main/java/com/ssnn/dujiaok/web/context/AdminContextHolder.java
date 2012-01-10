package com.ssnn.dujiaok.web.context;

/**
 * 
 * @author shenjia.caosj 2012-1-10
 *
 */
public class AdminContextHolder {

	private static ThreadLocal<AdminContext> memberContextHolder = new ThreadLocal<AdminContext>() ;
	
	public static void setContext(AdminContext context){
		memberContextHolder.set(context);
	}
	
	public static AdminContext getContext(){
		return memberContextHolder.get() ;
	}
	
}
