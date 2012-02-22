package com.ssnn.dujiaok.web.context;

import com.ssnn.dujiaok.model.MemberDO;

/**
 * Member Context
 * @author shenjia.caosj 2011-12-27
 *
 */
public class MemberContext {

	private MemberDO member ;
	
	public String getMemberId(){
		if(member == null){
			return null ;
		}
		return member.getMemberId() ;
	}

	public MemberDO getMember() {
		return member;
	}

	public void setMember(MemberDO member) {
		this.member = member;
	}
	
	
	
	
}
