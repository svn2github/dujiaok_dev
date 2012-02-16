package com.ssnn.dujiaok.biz.common.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class JVMCacheClient implements CacheClient{

	private Map<String , Object> cache = new ConcurrentHashMap<String,Object>() ;
	
	@Override
	public boolean put(String key, Serializable value) {
		cache.put(key, value) ;
		return true ;
	}

	@Override
	public boolean put(String key, Serializable value, long expireTime) {
		cache.put(key, value) ;
		return true ;
	}

	@Override
	public <T> T get(String key) {
		return (T)cache.get(key) ;
	}

	@Override
	public Map<String, Object> getBulk(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getBulk(Collection<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String key) {
		cache.remove(key) ;
		return true ;
	}

	@Override
	public long incr(String key, int by) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long decr(String key, int by) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> boolean putIfAbsent(String key, T value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> boolean putIfAbsent(String key, T value, long expireTime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> Future<Boolean> asyncPutIfAbsent(String key, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Future<Boolean> asyncPutIfAbsent(String key, T value,
			long expireTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Future<Boolean> asyncPut(String key, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Future<Boolean> asyncPut(String key, T value, long expireTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Future<T> asyncGet(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Map<String, Object>> asyncGetBulk(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Map<String, Object>> asyncGetBulk(Collection<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> asyncDelete(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
