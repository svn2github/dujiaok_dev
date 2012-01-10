package com.ssnn.dujiaok.model;

import java.util.Date;

/**
 * 管理员
 * @author shenjia.caosj 2012-1-10
 *
 */
public class AdminDO {

	private int id ;
	/**
	 * 用户名
	 */
	private String username ;
	
	/**
	 * 密码
	 */
	private String password ;
	
	private Date gmtCreate ;
	
	private Date gmtModified ;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	
}
