package com.ssnn.dujiaok.web.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.ContextHolder;
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
	
		String memberId = ContextHolder.getMemberContext().getMemberId() ;
		//未登录
		if(StringUtils.isBlank(memberId)){
			RequestContext reqContext = new RequestContext() ;
			reqContext.put(RequestContext.KEY_DONE_URL, ServletActionContext.getRequest().getServletPath()) ;
			ContextHolder.setRequestContext(reqContext) ;
			return BasicAction.MEMBER_NOT_LOGIN ;
		}
		String result = invocation.invoke();
		return result ;
	}

}
