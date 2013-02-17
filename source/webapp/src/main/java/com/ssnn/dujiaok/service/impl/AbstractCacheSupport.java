package com.ssnn.dujiaok.service.impl;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.ssnn.dujiaok.model.AbstractModel;
import com.ssnn.dujiaok.service.cache.CacheClient;

/**
 * @author langben
 */
public abstract class AbstractCacheSupport implements InitializingBean {

	public static final int ONE_YEAR_MILLISECONDS = 3600 * 1000 * 24 * 365; // 1年

	public static final int ONE_HOUR_MILLISECONDS = 3600 * 1000; // 1小时

	public static final int ONE_DAY_MILLISECONDS = 3600 * 1000 * 24; // 1天

	/**
	 * Cache RegionName
	 */
	private String regionName;
	   /**
     * Cache Client
     */
    private CacheClient cacheClient;

	public <T> boolean putValue(String key, T value) {
		InternalStoreItem item = new InternalStoreItem(value);
		return cacheClient.put(buildCacheKey(key), item);
	}

	public <T> T getValue(String key) {
		InternalStoreItem item = cacheClient.get(buildCacheKey(key));
		if (item == null) {
			return null;
		}
		return (T) item.getItem();
	}

	public boolean clearKey(String key) {
		return cacheClient.delete(buildCacheKey(key));
	}

	private String buildCacheKey(String key) {
		return regionName + "_" + key;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public CacheClient getCacheClient() {
		return cacheClient;
	}

	public void setCacheClient(CacheClient cacheClient) {
		this.cacheClient = cacheClient;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (cacheClient == null) {
			throw new IllegalStateException("cacheClient is null !");
		}
		if (StringUtils.isBlank(regionName)) {
			throw new IllegalStateException("regionName is Blank !");
		}
	}
	
	protected String buildCachekeyWithArgs(Object... args){
		String regionName = getRegionName() ;
		StringBuilder key = new StringBuilder(regionName) ;
		if(!ArrayUtils.isEmpty(args)) {
			for(Object a : args){
				if(a != null){
					key.append("_").append(a) ;
				}
			}
		}
		return key.toString() ;
	}
	
	public String getRegionName() {
		return regionName;
	}

	/**
	 * 缓存对象
	 * 
	 * @author langben 2011-4-12
	 * 
	 * @param <T>
	 */
	public static class InternalStoreItem<T> extends AbstractModel implements Serializable {
		
		public InternalStoreItem (T item){
			this.item = item ;
			this.gmtCreate = new Date() ;
		}
		
		private static final long serialVersionUID = 1L;
		private Date gmtCreate; // 缓存修改时间
		private T item; // 缓存数据item
		private int version = 0 ;

		public Date getGmtCreate() {
			return gmtCreate;
		}

		public void setGmtCreate(Date gmtCreate) {
			this.gmtCreate = gmtCreate;
		}

		public T getItem() {
			return item;
		}

		public void setItem(T item) {
			this.item = item;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}
		
	}
}
