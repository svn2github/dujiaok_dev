package com.ssnn.dujiaok.biz.dal.ibatis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.dal.AdminDAO;
import com.ssnn.dujiaok.model.AdminDO;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;

@ContextConfiguration(locations={
		"classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml",
		"classpath:bean/biz-datasource.xml" ,
	})
public class IBatisAdminDAOTest extends AbstractBaseJUnit4Test{

	@Autowired
	private AdminDAO adminDAO ;
	
	@Test
	public void test_getAdmin(){
		AdminDO admin = adminDAO.queryAdmin("admin", "1111112") ;
		int a = 5;
	}
	
}
