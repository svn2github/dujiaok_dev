package com.ssnn.dujiaok.biz.service.impl;

import com.ssnn.dujiaok.biz.dal.MemberDAO;
import com.ssnn.dujiaok.biz.exception.MemberExistsException;
import com.ssnn.dujiaok.biz.exception.MemberOrPasswordIncorrectException;
import com.ssnn.dujiaok.biz.service.MemberService;
import com.ssnn.dujiaok.model.MemberDO;

public class MemberServiceImpl implements MemberService {

	private MemberDAO memberDAO ;

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	@Override
	public void register(MemberDO member) throws MemberExistsException {
		MemberDO m = memberDAO.queryMember(member.getMemberId()) ;
		if(m != null){
			throw new MemberExistsException() ;
		} 
		memberDAO.createMember(member) ;
	}

	@Override
	public MemberDO login(String memberId, String password)
			throws MemberOrPasswordIncorrectException {
		MemberDO m = memberDAO.queryMember(memberId,password) ;
		if(m == null){
			throw new MemberOrPasswordIncorrectException() ;
		}
		return m ;
	}

	@Override
	public void updatePassword(String memberId, String password) {
		memberDAO.updatePassword(memberId, password) ;
	}

	@Override
	public void updateMemberInfo(MemberDO member) {
		memberDAO.updateMemberInfo(member) ;
	}

	@Override
	public MemberDO queryMember(String memberId) {
		return memberDAO.queryMember(memberId) ;
	}

	@Override
	public MemberDO queryMember(String memberId, String password) {
		return memberDAO.queryMember(memberId, password) ;
	}

}
