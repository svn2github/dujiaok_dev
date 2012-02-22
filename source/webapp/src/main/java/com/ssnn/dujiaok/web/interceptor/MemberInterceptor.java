package com.ssnn.dujiaok.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.web.constant.SessionConstant;
import com.ssnn.dujiaok.web.context.ContextHolder;
import com.ssnn.dujiaok.web.context.MemberContext;

public class MemberInterceptor extends AbstractInterceptor {

private static final Log logger = LogFactory.getLog(MemberInterceptor.class) ;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ContextHolder.setMemberContext(null) ;
		ContextHolder.setRequestContext(null) ;
		ActionContext ctx = ActionContext.getContext();
		//非集群，时间够的情况下改成Cookie
		Object o = ctx.getSession().get(SessionConstant.SESSION_MEMBER) ;
				
		MemberContext memberCtx = new MemberContext() ;
		if(o != null){
			MemberDO member = (MemberDO)o ;
			memberCtx.setMember(member) ;
		}
		ContextHolder.setMemberContext(memberCtx) ;
		
		//设置Context
		String result = invocation.invoke();
		ContextHolder.setMemberContext(null) ;
		ContextHolder.setRequestContext(null) ;
		return result ;
	}
}
