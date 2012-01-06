package com.ssnn.dujiaok.web.session;

import javax.servlet.http.HttpSession;

public class SessionManager {

	public static void setSession(HttpSession session , String key , Object value){
		session.setAttribute(key, value) ;
	}
}
