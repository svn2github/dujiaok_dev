package com.ssnn.dujiaok.web.action.member;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.exception.MemberExistsException;
import com.ssnn.dujiaok.biz.exception.MemberOrPasswordIncorrectException;
import com.ssnn.dujiaok.biz.service.MemberService;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.constant.SessionConstant;

public class RegisterAction extends BasicAction implements ModelDriven<MemberDO>{
	
	private MemberDO member = new MemberDO() ;
	private MemberService memberService ;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	

	public void setMember(MemberDO member) {
		this.member = member;
	}


	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String doRegister() throws Exception {
		try{
			memberService.register(member) ;
			getSession().put(SessionConstant.SESSION_MEMBER, member) ;
			return SUCCESS ;
		}catch(MemberExistsException e){
			//用户名密码错误
			addActionError("改用户名已经被使用") ;
			return INPUT ;
		}
	}
	@Override
	public MemberDO getModel() {
		return member ;
	}
}
