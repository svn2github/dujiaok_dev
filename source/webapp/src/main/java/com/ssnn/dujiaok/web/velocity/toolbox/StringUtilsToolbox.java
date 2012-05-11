package com.ssnn.dujiaok.web.velocity.toolbox;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
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
	
	public boolean isNotBlank(String str){
        return StringUtils.isNotBlank(str) ;
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
	
	public String encode(String str){
		try {
			return URLEncoder.encode(str, "UTF-8") ;
		} catch (UnsupportedEncodingException e) {
		}
		return str ;
	}
	
	public String decode(String str){
		try {
			return URLDecoder.decode(str, "UTF-8") ;
		} catch (UnsupportedEncodingException e) {
		}
		return str ;
	}
	
	public String replace(String text , String searchString , String replacement){
		return StringUtils.replace(text, searchString, replacement) ;
	}
	
	
	
	/**
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public Set<Map.Entry<String,String>> combineQueryArray(List<String> list1 , List<String> list2){
		Map<String,String> queryMap = new HashMap<String,String>() ;
		if(!CollectionUtils.isEmpty(list1)){
			for(String keyval : list1){
				String[] a = StringUtils.split(keyval,":");
				if(a != null && a.length == 2){
					queryMap.put(a[0],a[1]) ;
				}
			}
		}
		if(!CollectionUtils.isEmpty(list2)){
			for(String keyval : list2){
				String[] a = StringUtils.split(keyval,":");
				if(a!=null){
					if(a.length == 1){
						queryMap.put(a[0],"") ;
					}else if(a.length == 2){
						queryMap.put(a[0],a[1]) ;
					}
					
				}
			}
		}
		
		return queryMap.entrySet();
	}
}
