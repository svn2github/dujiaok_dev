package com.ssnn.dujiaok.web.context;

public class ContextHolder {

	private static ThreadLocal<MemberContext> memberContextHolder = new ThreadLocal<MemberContext>() ;
	
	private static ThreadLocal<RequestContext> requestContextHolder = new ThreadLocal<RequestContext>() ;
	
	public static void setMemberContext(MemberContext context){
		memberContextHolder.set(context) ;
	}
	
	public static MemberContext getMemberContext(){
		return memberContextHolder.get() ;
	}

	public static void setRequestContext(RequestContext context){
		requestContextHolder.set(context) ;
	}
	
	public static RequestContext getRequestContext(){
		return requestContextHolder.get() ;
	}
	
	
}
