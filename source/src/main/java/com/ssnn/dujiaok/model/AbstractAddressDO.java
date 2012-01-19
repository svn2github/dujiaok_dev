package com.ssnn.dujiaok.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author shenjia.caosj 2012-1-19
 *
 */
public abstract class AbstractAddressDO {

	private String code ;
	
	private String name ;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this , ToStringStyle.SHORT_PREFIX_STYLE) ;
	}
	
}
