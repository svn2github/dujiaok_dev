package com.ssnn.dujiaok.biz.service.cache;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.ssnn.dujiaok.model.HotCityDO;
import com.ssnn.dujiaok.service.cache.CacheClient;
import com.ssnn.dujiaok.service.impl.AbstractCacheSupport;

public class HotCityCacheImpl extends AbstractCacheSupport implements HotCityCache {

	@Override
	public List<HotCityDO> getHotCitys() {
		CacheClient cc = getCacheClient() ;
		String key = buildCachekeyWithArgs() ;
		InternalStoreItem<List<HotCityDO>> store = cc.get(key) ;
		if(store == null){
			return null ;
		}
		Date create = store.getGmtCreate() ;
		if(create == null){
			return null ;
		}
		Date now = new Date() ;
		create = DateUtils.addMinutes(create, 5) ; //5分钟过期
		if(now.after(create)) {
			return null ;
		}
		return store.getItem() ;
	}

	@Override
	public void setHotCitys(List<HotCityDO> list) {
		CacheClient cc = getCacheClient() ;
		String key = buildCachekeyWithArgs() ;
		InternalStoreItem<List<HotCityDO>> store = new InternalStoreItem<List<HotCityDO>>(list) ; 
		cc.put(key, store) ;
	}
	
	@Override
	public void clear() {
		CacheClient cc = getCacheClient() ;
		String key = buildCachekeyWithArgs() ;
		cc.delete(key) ;
	}
	
}
