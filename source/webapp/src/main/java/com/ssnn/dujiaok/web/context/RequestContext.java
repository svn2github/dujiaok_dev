package com.ssnn.dujiaok.web.context;

import java.util.HashMap;
import java.util.Map;

public class RequestContext {
	
	public static final String KEY_DONE_URL = "doneUrl" ;

	private static Map<String,Object> parameters = new HashMap<String,Object>() ;
	
	public void put(String key ,Object value){
		parameters.put(key, value) ;
	}
	
	public Object get(String key){
		return parameters.get(key) ;
	}
}
