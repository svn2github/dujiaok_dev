package com.ssnn.dujiaok.biz.service.cache;

import com.ssnn.dujiaok.model.FrontConfigDO;

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
}
