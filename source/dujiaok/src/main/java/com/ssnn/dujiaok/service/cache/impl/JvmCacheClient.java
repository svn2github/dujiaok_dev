package com.ssnn.dujiaok.service.cache.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import com.ssnn.dujiaok.service.cache.CacheClient;

/**
 * 类JvmCacheClient.java的实现描述：TODO 类实现描述
 * 
 * @author ib 2012-2-14 上午01:21:46
 */
public class JvmCacheClient implements CacheClient {

    private static Map<String, Object> data = new ConcurrentHashMap<String, Object>();

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#put(java.lang.String, java.io.Serializable)
     */
    @Override
    public boolean put(String key, Serializable value) {
        data.put(key, value);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#put(java.lang.String, java.io.Serializable, long)
     */
    @Override
    public boolean put(String key, Serializable value, long expireTime) {
        data.put(key, value);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#get(java.lang.String)
     */
    @Override
    public <T> T get(String key) {
        return (T) data.get(key);
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#delete(java.lang.String)
     */
    @Override
    public boolean delete(String key) {
        data.remove(key);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#asyncDelete(java.lang.String)
     */
    @Override
    public Future<Boolean> asyncDelete(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#asyncGet(java.lang.String)
     */
    @Override
    public <T> Future<T> asyncGet(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#asyncGetBulk(java.lang.String[])
     */
    @Override
    public Future<Map<String, Object>> asyncGetBulk(String... keys) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#asyncGetBulk(java.util.Collection)
     */
    @Override
    public Future<Map<String, Object>> asyncGetBulk(Collection<String> keys) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#asyncPut(java.lang.String, java.lang.Object)
     */
    @Override
    public <T> Future<Boolean> asyncPut(String key, T value) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#asyncPut(java.lang.String, java.lang.Object, long)
     */
    @Override
    public <T> Future<Boolean> asyncPut(String key, T value, long expireTime) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#asyncPutIfAbsent(java.lang.String, java.lang.Object)
     */
    @Override
    public <T> Future<Boolean> asyncPutIfAbsent(String key, T value) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#asyncPutIfAbsent(java.lang.String, java.lang.Object, long)
     */
    @Override
    public <T> Future<Boolean> asyncPutIfAbsent(String key, T value, long expireTime) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#decr(java.lang.String, int)
     */
    @Override
    public long decr(String key, int by) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#getBulk(java.lang.String[])
     */
    @Override
    public Map<String, Object> getBulk(String... keys) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#getBulk(java.util.Collection)
     */
    @Override
    public Map<String, Object> getBulk(Collection<String> keys) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#incr(java.lang.String, int)
     */
    @Override
    public long incr(String key, int by) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#putIfAbsent(java.lang.String, java.lang.Object)
     */
    @Override
    public <T> boolean putIfAbsent(String key, T value) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.cache.CacheClient#putIfAbsent(java.lang.String, java.lang.Object, long)
     */
    @Override
    public <T> boolean putIfAbsent(String key, T value, long expireTime) {
        // TODO Auto-generated method stub
        return false;
    }

}
