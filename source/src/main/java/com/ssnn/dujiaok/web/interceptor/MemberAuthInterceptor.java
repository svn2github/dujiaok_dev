package com.ssnn.dujiaok.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 判断会员有没登陆
 * @author shenjia.caosj 2011-12-27
 *
 */
public class MemberAuthInterceptor extends  AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		return invocation.invoke();
	}

}
