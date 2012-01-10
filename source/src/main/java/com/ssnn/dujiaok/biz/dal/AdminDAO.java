package com.ssnn.dujiaok.biz.dal;

import com.ssnn.dujiaok.model.AdminDO;

/**
 * ADMIN DAO
 * @author shenjia.caosj 2012-1-10
 *
 */
public interface AdminDAO {

	/**
	 * 查询管理员
	 * @param username
	 * @param password
	 * @return
	 */
	AdminDO queryAdmin(String username,String password) ;
}
