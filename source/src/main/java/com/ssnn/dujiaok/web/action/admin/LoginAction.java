package com.ssnn.dujiaok.web.action.admin;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.exception.MemberOrPasswordIncorrectException;
import com.ssnn.dujiaok.biz.service.AdminService;
import com.ssnn.dujiaok.model.AdminDO;
import com.ssnn.dujiaok.util.WhitelistUtils;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.constant.SessionConstant;
import com.ssnn.dujiaok.web.session.SessionManager;

public class LoginAction extends BasicAction {

	private String Done  ;
	
	private String username ;
	
	private String password ;
	
	private AdminService adminService ;
	
	
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public String getDone() {
		return Done;
	}

	public void setDone(String done) {
		Done = done;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String execute() throws Exception {
		
		String Done = getRequest().getParameter("Done") ;
				
		this.Done = Done ;
		
		return SUCCESS ;
		
		
	}
	
	public String doLogin() throws Exception{
	
		AdminDO m = adminService.login(username, password) ;
		if(m == null){
			//用户名密码错误
			addActionError("用户名或密码错误") ;
			return INPUT ;
		}
		//登陆成功
		SessionManager.setSession(getHttpSession()	, SessionConstant.SESSION_ADMIN	, m) ;
		
		if(StringUtils.isBlank(Done) || !WhitelistUtils.isWhitelistUrl(Done)){
			return SUCCESS;
		}else{
			getResponse().sendRedirect(Done) ;
			return null ;
		}
		
	}
}
