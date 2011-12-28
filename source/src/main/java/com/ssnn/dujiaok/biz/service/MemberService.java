package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.model.MemberDO;

public interface MemberService {

	void register(MemberDO member) ;
	
	boolean login(String memberId , String password) ;
	
	void updatePassword(String memberId , String password) ;
	
	void updateMemberInfo(MemberDO member) ;
}
