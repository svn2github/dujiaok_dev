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
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.util.EnvPropertiesUtil;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.ContextHolder;
import com.ssnn.dujiaok.web.context.MemberContext;
import com.ssnn.dujiaok.web.context.RequestContext;
import com.ssnn.dujiaok.web.velocity.toolbox.EnvPropertiesToolbox;

/**
 * 判断会员有没登陆
 * @author shenjia.caosj 2011-12-27
 *
 */
public class MemberAuthInterceptor extends AbstractInterceptor {

	private static final Log logger = LogFactory.getLog(MemberAuthInterceptor.class) ;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String memberId = null;
		ActionContext context = invocation.getInvocationContext();
		MemberDO memberDO = (MemberDO) (context.getSession().get("session_member"));
//		MemberContext context = ContextHolder.getMemberContext();
		if (memberDO != null) {
			memberId = memberDO.getMemberId();
		}
		//未登录
		if(StringUtils.isBlank(memberId)){	
			HttpServletRequest request = ServletActionContext.getRequest() ;
			String toUrl = request.getRequestURL().toString() ;
			String requestQueryString = request.getQueryString() ;
			if(StringUtils.isNotBlank(requestQueryString)) {
				toUrl += "?" + requestQueryString ;
			}
			toUrl = URLEncoder.encode(toUrl,Constant.ENCODING) ;
			
			invocation.getInvocationContext().put(Constant.REDIRECT_KEY , toUrl ) ;
			
			return "memberLogin" ;	
		}
		
		String result = invocation.invoke();
		return result ;
	}

}
