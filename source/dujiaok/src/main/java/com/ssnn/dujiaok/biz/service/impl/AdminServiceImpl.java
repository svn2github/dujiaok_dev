package com.ssnn.dujiaok.biz.service.impl;

import com.ssnn.dujiaok.biz.dal.AdminDAO;
import com.ssnn.dujiaok.biz.service.AdminService;
import com.ssnn.dujiaok.model.AdminDO;

public class AdminServiceImpl implements AdminService{

	private AdminDAO adminDAO ;
	
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	@Override
	public AdminDO login(String username, String password) {
		AdminDO admin = adminDAO.queryAdmin(username, password) ;
		return admin ;
	}

}
