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

	/**
	 * 注册
	 * @param member
	 * @throws MemberExistsException
	 */
	void register(MemberDO member) throws MemberExistsException;
	
	/**
	 * 登陆
	 * @param memberId
	 * @param password
	 * @return
	 * @throws MemberOrPasswordIncorrectException
	 */
	void login(String memberId , String password) throws MemberOrPasswordIncorrectException  ;
	
	/**
	 * 更新密码
	 * @param memberId
	 * @param password
	 */
	void updatePassword(String memberId , String password) ;
	
	/**
	 * 更新会员信息
	 * @param member
	 */
	void updateMemberInfo(MemberDO member) ;
}
