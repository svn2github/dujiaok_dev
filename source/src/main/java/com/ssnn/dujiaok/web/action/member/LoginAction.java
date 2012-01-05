package com.ssnn.dujiaok.web.action.member;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.constant.SessionConstant;
import com.ssnn.dujiaok.web.context.ContextHolder;
import com.ssnn.dujiaok.web.context.RequestContext;

public class LoginAction extends BasicAction{
	
	private String Done  ;
	
	private String memberId ;
	
	private String password ;
		
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDone() {
		return Done;
	}

	public void setDone(String done) {
		Done = done;
	}

	@Override
	public String execute() throws Exception {
		
		String Done = getRequest().getParameter("Done") ;
		if(StringUtils.isBlank(Done)){
			RequestContext c  = ContextHolder.getRequestContext();
			if(c != null){
				Done = (String)c.get(RequestContext.KEY_DONE_URL) ;
			}
		}	
		
		this.Done = Done ;
		//
		//do login
//		
//		if(StringUtils.isBlank(Done) || !isSafeUrl(Done)){
//			return "home";
//		}else{
//			ServletActionContext.getResponse().sendRedirect(Done) ;
//			return null ;
//		}
		
		return SUCCESS ;
		
		
	}
	
	public String doLogin() throws Exception{
		MemberDO m = new MemberDO() ;
		m.setMemberId(this.memberId);
		m.setNickname("马德福") ;
		//FIXME FROM DB
		
		//登陆成功
		getSession().put(SessionConstant.SESSION_MEMBER, m) ;
		getHttpSession().setAttribute(SessionConstant.SESSION_MEMBER, m) ;
		if(StringUtils.isBlank(Done) || !isSafeUrl(Done)){
			return SUCCESS;
		}else{
			getResponse().sendRedirect(Done) ;
			return null ;
		}
	}
	
	private boolean isSafeUrl(String url){
		if(!StringUtils.startsWith(url, "http")){
			return false ;
		}
		//white list 
		return true ;
	}
}
