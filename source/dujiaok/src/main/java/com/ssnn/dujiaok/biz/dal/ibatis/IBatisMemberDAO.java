package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.MemberDAO;
import com.ssnn.dujiaok.model.MemberDO;

public class IBatisMemberDAO extends SqlMapClientDaoSupport implements MemberDAO{

	@Override
	public MemberDO queryMember(String memberId) {	
		return (MemberDO)getSqlMapClientTemplate().queryForObject("member.queryMember",memberId) ;
	}

	@Override
	public MemberDO queryMember(String memberId, String password) {
		Map<String,String> condition = new HashMap<String,String>() ;
		condition.put("memberId", memberId) ;
		condition.put("password", password) ;
		return (MemberDO)getSqlMapClientTemplate().queryForObject("member.queryMemberByMemberidAndPassword",condition) ;
	}

	@Override
	public void createMember(MemberDO member) {
		getSqlMapClientTemplate().insert("member.createMember", member) ;
	}

	@Override
	public void updateMemberInfo(MemberDO member) {
		getSqlMapClientTemplate().update("member.updateMemberInfo" , member) ;
	}

	@Override
	public void updatePassword(String memberId, String password) {
		Map<String,String> condition = new HashMap<String,String>() ;
		condition.put("memberId", memberId) ;
		condition.put("password", password) ;
		getSqlMapClientTemplate().update("member.updatePassword" , condition) ;
	}

}
