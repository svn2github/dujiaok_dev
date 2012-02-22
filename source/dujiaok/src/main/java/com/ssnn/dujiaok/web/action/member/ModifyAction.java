package com.ssnn.dujiaok.web.action.member;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.MemberService;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.constant.SessionConstant;
import com.ssnn.dujiaok.web.context.ContextHolder;
import com.ssnn.dujiaok.web.session.SessionManager;

public class ModifyAction extends BasicAction implements ModelDriven<MemberDO> {

	private MemberDO member = new MemberDO();
		
	private String newPassword ;
	
	private MemberService memberService ;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public MemberDO getMember() {
		return member;
	}

	public void setMember(MemberDO member) {
		this.member = member;
	}
	
	@Override
	public String execute() throws Exception {
		String memberId = ContextHolder.getMemberContext().getMemberId() ;
		member = memberService.queryMember(memberId) ;
		return SUCCESS ;	
	}

	public String doModify() throws Exception {
		String memberId = ContextHolder.getMemberContext().getMemberId() ;
		member.setMemberId(memberId) ;
		memberService.updateMemberInfo(member) ;
		SessionManager.setSession(getHttpSession(), SessionConstant.SESSION_MEMBER, member) ;
		addActionError("用户修改成功") ;
		return SUCCESS ;
	}
	
	public String doModifyPassword() throws Exception {
		String memberId = ContextHolder.getMemberContext().getMemberId() ;
		MemberDO m = memberService.queryMember( memberId , member.getPassword() ) ;
		if(m==null){
			addActionError("原密码错误") ;
			return INPUT ;
		}
		memberService.updatePassword(memberId, newPassword ) ;
		addActionError("密码更新成功") ;
		return SUCCESS ;
	}

	@Override
	public MemberDO getModel() {
		return member ;
	}
}
