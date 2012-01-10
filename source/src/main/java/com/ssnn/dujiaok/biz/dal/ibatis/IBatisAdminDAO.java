package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.AdminDAO;
import com.ssnn.dujiaok.model.AdminDO;


public class IBatisAdminDAO extends SqlMapClientDaoSupport implements AdminDAO{

	@Override
	public AdminDO queryAdmin(String username, String password) {
		Map<String,String> condition = new HashMap<String,String>() ;
		condition.put("username", username) ;
		condition.put("password", password) ;
		return (AdminDO)getSqlMapClientTemplate().queryForObject("admin.queryAdminByUsernameAndPassword", condition) ;
	}

}
