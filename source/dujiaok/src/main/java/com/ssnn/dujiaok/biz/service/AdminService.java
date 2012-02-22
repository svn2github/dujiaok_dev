package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.model.AdminDO;

/**
 * 
 * @author shenjia.caosj 2012-1-10
 *
 */
public interface AdminService {

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	AdminDO login(String username , String password) ;
}
