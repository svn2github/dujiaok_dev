package com.ssnn.dujiaok.model;

/**
 * 
 * @author shenjia.caosj 2011-12-27
 *
 */
public class MemberDO {

	
	/**
	 * 登陆id
	 */
	private String memberId ;
	
	/**
	 * 昵称
	 */
	private String nickName ;
	
	/**
	 * 手机
	 */
	private String mobileNo ;
	
	/**
	 * email
	 */
	private String email ;

	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
