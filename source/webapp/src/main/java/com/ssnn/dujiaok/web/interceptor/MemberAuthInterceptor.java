package com.ssnn.dujiaok.web.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.web.constant.SessionConstant;
import com.ssnn.dujiaok.web.context.ContextHolder;

/**
 * 判断会员有没登陆
 * 
 * @author langben 2011-12-27
 * 
 */
public class MemberAuthInterceptor extends AbstractInterceptor {

	private static final Log logger = LogFactory.getLog(MemberAuthInterceptor.class);

	private boolean auth = true ;

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		String memberId = null;
		ActionContext context = invocation.getInvocationContext();
		MemberDO memberDO = (MemberDO) (context.getSession().get(SessionConstant.SESSION_MEMBER));

		if (memberDO != null) {
			memberId = memberDO.getMemberId();
			ContextHolder.getMemberContext().setMember(memberDO) ;
		}
		
		
		
		// 未登录
		if(auth){
			if (StringUtils.isBlank(memberId)) {
				HttpServletRequest request = ServletActionContext.getRequest();
				String toUrl = request.getRequestURL().toString();
				String requestQueryString = request.getQueryString();
				if (StringUtils.isNotBlank(requestQueryString)) {
					toUrl += "?" + requestQueryString;
				}
				toUrl = URLEncoder.encode(toUrl, Constant.ENCODING);
	
				invocation.getInvocationContext().put(Constant.REDIRECT_KEY, toUrl);
	
				return "memberLogin";
			}
		}

		try {
			String result = invocation.invoke();
			return result;
		} finally {
			ContextHolder.getMemberContext().setMember(null) ;
		}
		
	}

}
