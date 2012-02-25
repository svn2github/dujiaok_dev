package com.ssnn.dujiaok.service.impl;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.ssnn.dujiaok.service.cache.CacheClient;

/**
 * @author shenjia.caosj
 */
public abstract class AbstractCacheSupport implements InitializingBean {

	public static final int ONE_YEAR_MILLISECONDS = 3600 * 1000 * 24 * 365; // 1年

	public static final int ONE_HOUR_MILLISECONDS = 3600 * 1000; // 1小时

	public static final int ONE_DAY_MILLISECONDS = 3600 * 1000 * 24; // 1天

	/**
	 * Cache RegionName
	 */
	private String regionName;

	public <T> boolean putValue(String key, T value) {
		InternalStoreItem item = new InternalStoreItem();
		item.setItem(value);
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

	/**
	 * Cache Client
	 */
	private CacheClient cacheClient;

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

	/**
	 * @param <T>
	 */
	private static class InternalStoreItem<T> implements Serializable {

		private static final long serialVersionUID = 1L;
		private Date modifyTime; //
		private T item; //

		public InternalStoreItem() {
			this.modifyTime = new Date();
		}

		public Date getModifyTime() {
			return modifyTime;
		}

		public void setModifyTime(Date modifyTime) {
			this.modifyTime = modifyTime;
		}

		public T getItem() {
			return item;
		}

		public void setItem(T item) {
			this.item = item;
		}
	}
}
