package com.ssnn.dujiaok.model;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author shenjia.caosj 2011-12-27
 *
 */
public class MemberDO {

	private int id ;
	
	/**
	 * 登陆id
	 */
	private String memberId ;
	
	/**
	 * password
	 */
	private String password ;
	
	/**
	 * 昵称
	 */
	private String nickname ;
	
	/**
	 * 手机
	 */
	private String mobileNo ;
	
	/**
	 * email
	 */
	private String email ;

	private Date gmtCreate ;
	
	private Date gmtModified ;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE) ;
	}
	
}
