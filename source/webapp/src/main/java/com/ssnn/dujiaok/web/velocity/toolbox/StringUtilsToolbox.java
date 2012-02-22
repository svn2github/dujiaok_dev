package com.ssnn.dujiaok.web.velocity.toolbox;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author shenjia.caosj 2012-1-20
 *
 */
public class StringUtilsToolbox {

	public boolean isBlank(String str){
		return StringUtils.isBlank(str) ;
	}
	
	public String trim(String str){
		return StringUtils.trim(str) ;
	}
	
	public String escapeHtml(String str){
		return StringEscapeUtils.escapeHtml(str) ;
	}
	
	public String escapeJS(String str){
		return StringEscapeUtils.escapeJavaScript(str) ;
	}
}
