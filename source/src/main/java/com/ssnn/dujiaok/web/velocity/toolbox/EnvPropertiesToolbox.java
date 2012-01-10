package com.ssnn.dujiaok.web.velocity.toolbox;

import com.ssnn.dujiaok.model.AdminDO;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.util.EnvPropertiesUtil;
import com.ssnn.dujiaok.web.context.AdminContextHolder;
import com.ssnn.dujiaok.web.context.ContextHolder;


/**
 * EnvPropertiesToolbox velocity toolbox
 * @author shenjia.caosj 2011-12-23
 *
 */
public class EnvPropertiesToolbox {

	private static final String KEY_DUJIAOK_DOMAIN_NAME = "dujiaok.domainName" ;
	private static final String KEY_DUJIAOK_PORT = "dujiaok.port" ;
	private static final String KEY_DUJIAOK_STATIC_URL = "dujiaok.static.url" ;
	
	private static String rootPath = null ;
	
	/**
	 * getProperty
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		return EnvPropertiesUtil.getProperty(key) ;
	}
	
	public String getRoot(){
		if(rootPath != null){
			return rootPath ;
		}
		String port = getProperty(KEY_DUJIAOK_PORT) ;
		rootPath = makeUrl("http" , getProperty(KEY_DUJIAOK_DOMAIN_NAME) , port) ;
		rootPath = rootPath + "/dujiaok" ;
		return rootPath ;
	}
	
	public static String makeUrl(String protocol , String domain , String port){
		String url = null ;
		if("80".equals(port)){
			url = protocol + "://" + domain ;
		}else{
			url = protocol + "://" + domain + ":" + port ;
		}
		return url ;
	}
	
	public String getPath(String relativePath){
		String path = getRoot() + relativePath ;
		return path ;
	}
	
	public String getStaticroot(){
		return getProperty(KEY_DUJIAOK_STATIC_URL) ;
	}
	
	public String getStaticpath(String relativePath){
		String path = getStaticroot() + relativePath ;
		return path ;
	}
	
	public MemberDO getMember(){
		if( ContextHolder.getMemberContext() == null ){
			return null ;
		}
		return ContextHolder.getMemberContext().getMember() ;
	}
	
	public AdminDO getAdmin(){
		if( AdminContextHolder.getContext()== null ){
			return null ;
		}
		return AdminContextHolder.getContext().getAdmin() ;
	}
}
