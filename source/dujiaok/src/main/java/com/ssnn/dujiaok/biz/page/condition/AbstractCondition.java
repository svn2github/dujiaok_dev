package com.ssnn.dujiaok.biz.page.condition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public abstract class AbstractCondition {
	
	
	private static final Log logger = LogFactory.getLog(AbstractCondition.class) ;
	
	public Map<String,Object> toConditionMap() {
		Map<String,Object> m = new HashMap<String,Object>() ;
		
		Field[] fields = this.getClass().getDeclaredFields() ;
		try{
			for(Field field : fields){
				String name = field.getName();
				if(StringUtils.equals("start", name) || StringUtils.equals("size", name)){
					continue ;
				}
				String strGet = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
	            Method methodGet = this.getClass().getDeclaredMethod(strGet);
	            Object object = methodGet.invoke(this);
	            Object value = object ;
	            if(value != null){
	            	m.put(name, value);
	            }
			}
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
		}
		return m ;
	}
}
