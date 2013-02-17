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
import com.ssnn.dujiaok.model.AdminDO;
import com.ssnn.dujiaok.web.constant.SessionConstant;
import com.ssnn.dujiaok.web.context.AdminContext;
import com.ssnn.dujiaok.web.context.AdminContextHolder;
import com.ssnn.dujiaok.web.context.ContextHolder;

/**
 * 管理员interceptor
 * @author langben 2012-1-10
 *
 */
public class AdminAuthInterceptor  extends AbstractInterceptor {

	private static final Log logger = LogFactory.getLog(AdminAuthInterceptor.class) ;
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
				
		ActionContext ctx = ActionContext.getContext();
		//非集群，时间够的情况下改成Cookie
		Object o = ctx.getSession().get(SessionConstant.SESSION_ADMIN) ;
				
		AdminContext adminContext = new AdminContext() ;
		if(o != null){
			AdminDO admin = (AdminDO)o ;
			adminContext.setAdmin(admin) ;
		}
		AdminContextHolder.setContext(adminContext) ;
		
		if(StringUtils.isNotBlank(adminContext.getUsername())){
			//已经登录
			//TODO 子模块权限
			return invocation.invoke() ;
		}
		//未登录
		HttpServletRequest request = ServletActionContext.getRequest() ;
		String toUrl = request.getRequestURL().toString() ;
		String requestQueryString = request.getQueryString() ;
		if(StringUtils.isNotBlank(requestQueryString)) {
			toUrl += "?" + requestQueryString ;
		}
		toUrl = URLEncoder.encode(toUrl,Constant.ENCODING) ;
		invocation.getInvocationContext().put(Constant.REDIRECT_KEY , toUrl ) ;
		
		return "adminLogin" ;	
	}

}
