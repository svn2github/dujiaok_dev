package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.biz.exception.MemberExistsException;
import com.ssnn.dujiaok.biz.exception.MemberOrPasswordIncorrectException;
import com.ssnn.dujiaok.model.MemberDO;

/**
 * MemberService
 * @author shenjia.caosj 2011-12-28
 *
 */
public interface MemberService {

	void register(MemberDO member) throws MemberExistsException;
	
	void login(String memberId , String password) throws MemberOrPasswordIncorrectException  ;
	
	void updatePassword(String memberId , String password) ;
	
	void updateMemberInfo(MemberDO member) ;
}
