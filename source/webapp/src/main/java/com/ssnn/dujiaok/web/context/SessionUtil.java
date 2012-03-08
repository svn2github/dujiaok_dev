package com.ssnn.dujiaok.web.context;

import javax.servlet.http.HttpSession;

import com.ssnn.dujiaok.model.MemberDO;

public final class SessionUtil {
	public static MemberDO getLoginMemberDO(HttpSession session) {
		if (session == null) {
			return null;
		}
		return (MemberDO) session.getAttribute("session_member");
	}
}
