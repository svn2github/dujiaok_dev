package com.ssnn.dujiaok.biz.dal;

import com.ssnn.dujiaok.model.MemberDO;

/**
 * 
 * @author langben 2011-12-27
 *
 */
public interface MemberDAO {

	/**
	 * 查询会员
	 * @param memberId
	 * @return
	 */
	MemberDO queryMember(String memberId) ;
	
	/**
	 * 根据会员ID和password查询Member
	 * @param memberId
	 * @param password
	 * @return
	 */
	MemberDO queryMember(String memberId , String password) ;
	
	/**
	 * 创建会员
	 * @param member
	 */
	void createMember(MemberDO member) ;

	/**
	 * 更新会员
	 * @param member
	 */
	void updateMemberInfo(MemberDO member) ;
	
	/**
	 * 更新密码
	 * @param memberId
	 * @param password
	 */
	void updatePassword(String memberId , String password) ;
	
	
}
