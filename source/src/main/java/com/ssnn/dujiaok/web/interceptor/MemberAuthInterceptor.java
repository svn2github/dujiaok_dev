package com.ssnn.dujiaok.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.constant.SessionConstant;
import com.ssnn.dujiaok.web.context.ContextHolder;
import com.ssnn.dujiaok.web.context.MemberContext;
import com.ssnn.dujiaok.web.context.RequestContext;

/**
 * 判断会员有没登陆
 * @author shenjia.caosj 2011-12-27
 *
 */
public class MemberAuthInterceptor extends AbstractInterceptor {

	private static final Log logger = LogFactory.getLog(MemberAuthInterceptor.class) ;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ContextHolder.setMemberContext(null) ;
		ContextHolder.setRequestContext(null) ;
		ActionContext ctx = ActionContext.getContext();
		//非集群，时间够的情况下改成Cookie
		Object o = ctx.getSession().get(SessionConstant.SESSION_MEMBER) ;
		if(o == null){
			RequestContext reqContext = new RequestContext() ;
			ContextHolder.setRequestContext(reqContext) ;
			ContextHolder.getRequestContext().put(RequestContext.KEY_DONE_URL, ServletActionContext.getRequest().getServletPath()) ;
			return BasicAction.MEMBER_NOT_LOGIN ;
		}
		MemberDO member = (MemberDO)o ;
		MemberContext memberCtx = new MemberContext() ;
		memberCtx.setMember(member) ;
		ContextHolder.setMemberContext(memberCtx) ;
		//设置Context
		String result = invocation.invoke();
		ContextHolder.setMemberContext(null) ;
		ContextHolder.setRequestContext(null) ;
		return result ;
	}

}
