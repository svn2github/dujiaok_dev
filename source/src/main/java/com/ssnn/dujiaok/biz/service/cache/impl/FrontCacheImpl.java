package com.ssnn.dujiaok.biz.service.cache.impl;

import com.ssnn.dujiaok.biz.common.cache.CacheClient;
import com.ssnn.dujiaok.biz.service.cache.FrontCache;
import com.ssnn.dujiaok.model.FrontConfigDO;

public class FrontCacheImpl extends AbstractCacheSupport implements FrontCache {

	@Override
	public FrontConfigDO getConfig(String key) {
		CacheClient cc = getCacheClient() ;
		String cacheKey = buildCacheKey(key) ;
		InternalStoreItem<FrontConfigDO> item = cc.get(cacheKey) ;
		if(item == null){
			return null ;
		}
		return item.getItem() ;
	}

	@Override
	public void setConfig(String key, FrontConfigDO config) {
		InternalStoreItem<FrontConfigDO> item = new InternalStoreItem<FrontConfigDO>() ;
		item.setItem(config) ;
		CacheClient cc = getCacheClient() ;
		String cacheKey = buildCacheKey(key) ;
		cc.put(cacheKey, item) ;
	}
	
	private String buildCacheKey(String key){
		return getRegionName() + "_" + key ;
	}

}
