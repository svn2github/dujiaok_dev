package com.ssnn.dujiaok.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;





/**
 * commonConfig.properties
 * @author shenjia.caosj 2011-5-17
 *
 */
public class EnvPropertiesUtil {
	private static final Log logger = LogFactory.getLog(EnvPropertiesUtil.class) ;
	private static final String PROPERTIES_PATH = "/bean/commonConfig.properties" ;
	
	private static Properties props = null ; 
	
	private static Lock mutex = new ReentrantLock() ;
	
	/**
	 *
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		if(props == null){
			mutex.lock();
			try{
				if(props == null){
					props = loadPropertiesFromPath(PROPERTIES_PATH) ;
					if(props == null){
						logger.error("cant load properties file :" + PROPERTIES_PATH) ;
						return null ;
					}
				}
			}finally{
				mutex.unlock() ;
			}
		}
		
		return props.getProperty(key) ;
	}
	
	//
	private static Properties loadPropertiesFromPath(String path) {
		Properties p = new Properties();
		InputStream in = null;
		try {
			Resource resource = new ClassPathResource(PROPERTIES_PATH) ;
			in = resource.getInputStream() ;
			p.load(in);
		} catch (Exception e) {
			logger.error("Error reading env.properties:" + e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
					logger.error("Error reading env.properties:" +  ex.getMessage(), ex);
				}
			}
		}
		return p ;
	}
}
