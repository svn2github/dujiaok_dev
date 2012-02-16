package com.ssnn.dujiaok.biz.service.cache.impl;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.ssnn.dujiaok.biz.common.cache.CacheClient;

/**
 * 
 * @author shenjia.caosj 
 *
 */
public class AbstractCacheSupport implements InitializingBean{

	protected static final int ONE_YEAR_MILLISECONDS  = 3600 * 1000 * 24 * 365; //1��
	
	protected static final int ONE_HOUR_MILLISECONDS = 3600 * 1000 ; //1Сʱ
	
	protected static final int ONE_DAY_MILLISECONDS = 3600 * 1000 * 24 ; //1��
	
	/**
	 * Cache RegionName 
	 */
	protected String regionName ;
	
	/**
	 * Cache Client 
	 */
	protected CacheClient cacheClient ;

	public String getRegionName() {
		return regionName;
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
		if(cacheClient == null){
			throw new IllegalStateException("cacheClient is null !") ;
		}
		if(StringUtils.isBlank(regionName)){
			throw new IllegalStateException("regionName is Blank !") ;
		}
	}

	/**
	 * 
	 * 
	 *
	 * @param <T>
	 */
	static class InternalStoreItem<T> implements Serializable{
		private static final long serialVersionUID = 1L;
		private Date modifyTime ; //�����޸�ʱ��
		private T item ; //�������item
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
