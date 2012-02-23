package com.ssnn.dujiaok.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ssnn.dujiaok.web.velocity.toolbox.EnvPropertiesToolbox;

@SuppressWarnings("serial")
public class SystemINIT extends HttpServlet {

	private static final Log logger = LogFactory.getLog(SystemINIT.class) ;
	
	@Override
	public void init() throws ServletException {
		String contextPath  = getServletContext().getContextPath() ;
		EnvPropertiesToolbox.setContextPath(contextPath) ;
		super.init();
	}
}
