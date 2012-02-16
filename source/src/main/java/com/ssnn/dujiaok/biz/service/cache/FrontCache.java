package com.ssnn.dujiaok.biz.service.cache;

import java.util.List;

import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.model.FrontViewDO;

/**
 * 前台缓存
 * @author shenjia.caosj 2012-2-16
 *
 */
public interface FrontCache {

	
	/**
	 * getConfig
	 * @param key
	 * @return
	 */
	FrontConfigDO getConfig(String key) ;
	
	/**
	 * setConfig
	 * @param key
	 * @param config
	 */
	void setConfig(String key , FrontConfigDO config) ;
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	List<FrontViewDO> getView(String key) ;
	
	/**
	 * 
	 * @param list
	 */
	void setView(String key ,List<FrontViewDO> list) ;
}
