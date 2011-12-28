package com.ssnn.dujiaok.biz.dal.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.dal.MemberDAO;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;



@ContextConfiguration(locations={
		"classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml",
		"classpath:bean/biz-datasource.xml" ,
	})
public class IBatisMemberDAOTest extends AbstractBaseJUnit4Test {
	
	@Autowired
	private MemberDAO memberDAO ;
	
	
	
	public void test_queryMember(){
		MemberDO m = memberDAO.queryMember("hello123") ;
		int a = 5 ;
		
	}
	
	
	public void test_queryMember_pass(){
		MemberDO m = memberDAO.queryMember("hello123","1243434") ;
		int a = 5 ;
	}
	
	
	public void test_createMember(){
		MemberDO member = new MemberDO() ;
		member.setEmail("ssnn@173.com") ;
		member.setMemberId("hello123") ;
		member.setMobileNo("13545454545") ;
		member.setNickname("刘强东");
		member.setPassword("1243434");
		memberDAO.createMember(member) ;
	}
	
	
	public void test_updateMemberInfo(){
		MemberDO m = memberDAO.queryMember("hello123") ;
		m.setEmail("caoshenjia@163.cin") ;
		m.setMobileNo("22222222") ;
		m.setNickname("aaaaaaaaaaa") ;
		m.setPassword("323232323232") ;
		memberDAO.updateMemberInfo(m) ; 
	}
	
	public void test_updatePassword(){
		memberDAO.updatePassword("hello123", "xaddwdwdw") ;
	}
}
