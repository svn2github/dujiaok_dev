package com.ssnn.dujiaok.biz.service.cache.impl;

import java.util.List;

import com.ssnn.dujiaok.biz.common.cache.CacheClient;
import com.ssnn.dujiaok.biz.service.cache.FrontCache;
import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.model.FrontViewDO;

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
	
	private String buildViewKey(String key){
		return getRegionName() + "_view" + key ;
	}

	@Override
	public List<FrontViewDO> getView(String key) {
		CacheClient cc = getCacheClient() ;
		String cacheKey = buildViewKey(key) ;
		InternalStoreItem<List<FrontViewDO>> item = cc.get(cacheKey) ;
		if(item == null){
			return null ;
		}
		return item.getItem() ;
	}

	@Override
	public void setView(String key , List<FrontViewDO> list) {
		InternalStoreItem<List<FrontViewDO>> item = new InternalStoreItem<List<FrontViewDO>>() ;
		item.setItem(list) ;
		CacheClient cc = getCacheClient() ;
		String cacheKey = buildViewKey(key) ;
		cc.put(cacheKey, item) ;
	}

}
