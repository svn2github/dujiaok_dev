package com.ssnn.dujiaok.web.context;

import com.ssnn.dujiaok.model.AdminDO;

/**
 * Admin Context
 * @author shenjia.caosj 2012-1-10
 *
 */
public class AdminContext {

	private AdminDO admin ;
	
	public String getUsername(){
		if(admin == null){
			return null ;
		}
		return admin.getUsername() ;
	}
	
	public AdminDO getAdmin(){
		return admin ;
	}
	
	public void setAdmin(AdminDO admin){
		this.admin = admin ;
	}
}
