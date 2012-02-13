package com.ssnn.dujiaok.service.cache.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ssnn.dujiaok.service.cache.CacheClient;

/**
 * XMemcached实现
 * @author shenjia.caosj 
 * @date 2010-11-11 下午04:21:20
 *
 */
public class XMemcachedClient implements CacheClient {

	private static final Log logger = LogFactory.getLog(XMemcachedClient.class) ;
	
	//protected static final long ONE_MONTH_SECONDS = 3600 * 24 * 30;
	
	protected static final long ONE_MONTH_MILLISECONDS = 3600L * 24 * 30 * 1000;
	
	protected MemcachedClient client; 
	
	protected ExecutorService executor;
	
	/**
	 * 过期时间，单位毫秒(ms)
	 */
	private long defaultExpireTime = -1; 
	
	public void setDefaultExpireTime(long defaultExpireTime) {
		this.defaultExpireTime = defaultExpireTime;
	}

	/**
	 * Constructors
	 */
	public XMemcachedClient(String servers){
		this(servers, ONE_MONTH_MILLISECONDS);
	}
	
	public XMemcachedClient(String servers ,long defaultExpireTime){
		MemcachedClientBuilder	builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers)) ;
		try{
			client = builder.build() ;
			this.defaultExpireTime = defaultExpireTime ;
		}catch(IOException e) {
			if(logger.isErrorEnabled()){
				logger.error("xmemcached init error ,"+e.getMessage() , e) ;
			}
		}
	}
	
	protected <T> Future<T> executeFuture(Callable<T> call) {
		FutureTask<T> future = new FutureTask<T>(call);
		this.executor.execute(future);
		return future;
	}
	
	
	@Override
	public Future<Boolean> asyncDelete(final String key) {
		return executeFuture(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return client.delete(key);
			}
		});
	}

	@Override
	public <T> Future<T> asyncGet(final String key) {
		return executeFuture(new Callable<T>() {
			public T call() throws Exception {
				return (T)client.get(key);
			}
		});
	}

	@Override
	public Future<Map<String, Object>> asyncGetBulk(final String... keys) {
		return asyncGetBulk(Arrays.asList(keys)) ;
	}

	@Override
	public Future<Map<String, Object>> asyncGetBulk(final Collection<String> keys) {
		return executeFuture(new Callable<Map<String, Object>>() {
			public Map<String, Object> call() throws Exception {
				return client.get(keys) ;
			}
		});
	}

	@Override
	public <T> Future<Boolean> asyncPut(final String key, final T value) {
		return asyncPut(key , value , this.defaultExpireTime) ;
	}

	@Override
	public <T> Future<Boolean> asyncPut(final String key,final T value,final long expireTime) {
		return executeFuture(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return client.set(key, (int)(expireTime/1000) ,value);
			}
		});
	}

	@Override
	public <T> Future<Boolean> asyncPutIfAbsent(String key, T value) {
		return asyncPutIfAbsent(key, value, this.defaultExpireTime) ;
	}

	@Override
	public <T> Future<Boolean> asyncPutIfAbsent(final String key, final T value, final long expireTime) {
		return executeFuture(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return client.add(key, (int)(expireTime/1000) , value);
			}
		});
		
	}

	@Override
	public long decr(String key, int by) {
		try{
			return client.decr(key, by) ;
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
		}
		return -1;
	}

	@Override
	public boolean delete(String key) {
		try{
			return client.delete(key) ;
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
		}
		return false;
	}

	@Override
	public <T> T get(String key) {
		try{
			return (T)client.get(key) ;
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
		}
		return null;
	}

	@Override
	public Map<String, Object> getBulk(String... keys) {
		try{
			return client.get(Arrays.asList(keys)) ;
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
		}
		return null;
	}

	@Override
	public Map<String, Object> getBulk(Collection<String> keys) {
		try{
			return client.get(keys) ;
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
		}
		return null;
	}

	@Override
	public long incr(String key, int by) {
		try{
			return client.incr(key, by);
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
		}
		return -1 ;
	}

	@Override
	public boolean put(String key, Serializable value) {
		return put(key , value , this.defaultExpireTime) ;
	}

	@Override
	public boolean put(String key, Serializable value, long expireTime) {
		try{
			return client.set(key, (int)(expireTime/1000), value) ;
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
		}
		return false ;
	}

	@Override
	public <T> boolean putIfAbsent(String key, T value) {
		return putIfAbsent(key , value , this.defaultExpireTime );
	}

	@Override
	public <T> boolean putIfAbsent(String key, T value, long expireTime) {
		try{
			return client.add(key, (int)(expireTime/1000), value) ;
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
		}
		return false;
	}

	
	
}
