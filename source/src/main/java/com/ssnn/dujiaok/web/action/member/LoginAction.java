package com.ssnn.dujiaok.web.action.member;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ValidationAware;
import com.ssnn.dujiaok.biz.exception.MemberOrPasswordIncorrectException;
import com.ssnn.dujiaok.biz.service.MemberService;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.util.WhitelistUtils;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.constant.SessionConstant;
import com.ssnn.dujiaok.web.session.SessionManager;

/**
 * 会员登录
 * 
 * @author shenjia.caosj 2012-1-10
 * 
 */
@SuppressWarnings("serial")
public class LoginAction extends BasicAction implements ValidationAware {

	private String Done;

	private String memberId;

	private String password;

	private MemberService memberService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getPassword() {
		return password;
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

		String Done = getRequest().getParameter("Done");
		this.Done = Done;
		return SUCCESS;

	}

	public String doLogin() throws Exception {
		try {
			MemberDO m = memberService.login(memberId, password);
			// 登陆成功
			SessionManager.setSession(getHttpSession(),
					SessionConstant.SESSION_MEMBER, m);

			if (StringUtils.isBlank(Done)
					|| !WhitelistUtils.isWhitelistUrl(Done)) {
				return SUCCESS;
			} else {
				getResponse().sendRedirect(Done);
				return null;
			}
		} catch (MemberOrPasswordIncorrectException e) {
			// 用户名密码错误
			addActionError("用户名或密码错误");

			return INPUT;
		}
	}

	public String logout() throws Exception {
		getSession().clear();
		return SUCCESS;
	}

}
