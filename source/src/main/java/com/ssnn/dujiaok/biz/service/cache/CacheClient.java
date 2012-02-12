package com.ssnn.dujiaok.biz.service.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 
 * @author shenjia.caosj 2012-2-11
 *
 */
public interface CacheClient {
			
		
	/**
	 * 通过同步方式缓存数据, 新数据将替换已有数据
	 * 该方法效率不如异步方式高, 除非有明确需求, 建议使用异步方法
	 * @param key
	 * @param value
	 * @return 是否保存成功
	 */
	boolean put(final String key, Serializable value);
	/**
	 * 通过同步方式缓存数据, 新数据将替换已有数据
	 * 该方法效率不如异步方式高, 除非有明确需求, 建议使用异步方法
	 * @param key
	 * @param value
	 * @param expireTime 过期时间,单位 milliseconds
	 * @return 是否成功
	 */
	boolean put(final String key, Serializable value, long expireTime);
	/**
	 * 通过键值获取缓存对象
	 * 同步方法
	 * @param <T>
	 * @param key
	 * @return 缓存的对象, 如果没有, 返回 null
	 */
	<T> T get(final String key);
	
	/**
	 * 通过同步的方式批量获取结果
	 * @param keys
	 * @return
	 */
	Map<String, Object> getBulk(String... keys);
	
	/**
	 * 通过同步的方式批量获取结果
	 * @param keys
	 * @return
	 */
	Map<String, Object> getBulk(Collection<String> keys);
	
	/**
	 * 同步方式删除key 
	 * @param key
	 * @return
	 */
	boolean delete(String key);

	/**
	 * Increment the given key by the given amount.
	 * @param key
	 * @param by
	 * @return -1, if the key is not found, the value after incrementing otherwise
	 */
	long incr(String key, int by);
	/**
	 * Decrement the given key by the given amount
	 * @param key
	 * @param by
	 * @return -1, if the key is not found, the value after decrementing otherwise
	 */
	long decr(String key, int by);
	
	/**
	 * 缓存数据, 如果已有数据, 当前新的值将不能生效
	 * @param <T> 保存数据的类型
	 * @param key
	 * @param value
	 * @return 是否保存成功
	 */
	<T> boolean putIfAbsent(final String key , final T value) ;
	
	/**
	 * 缓存数据, 如果已有数据, 当前新的值将不能生效
	 * @param <T> 保存数据的类型
	 * @param key
	 * @param value
	 * @param expireTime 过期时间,单位 milliseconds
	 * @return 是否保存成功
	 */
	<T> boolean putIfAbsent(final String key , final T value , final long expireTime) ;
	
	/**
	 * 以下为异步接口
	 */
	
	/**
	 * 通过异步方式缓存数据, 如果已有数据, 当前新的值将不能生效
	 * 当通过future.get()获取结果时, 当前线程才会被block
	 * @param <T> 保存数据的类型
	 * @param key
	 * @param value
	 * @return 是否保存成功
	 */
	<T> Future<Boolean> asyncPutIfAbsent(final String key, final T value);
	/**
	 * 通过异步方式缓存数据, 如果已有数据, 当前新的值将不能生效
	 * 当通过future.get()获取结果时, 当前线程才会被block
	 * @param <T> 保存数据的类型
	 * @param key
	 * @param value
	 * @param expireTime 过期时间 ,单位 milliseconds
	 * @return 是否保存成功
	 */
	<T> Future<Boolean> asyncPutIfAbsent(final String key, final T value, final long expireTime);
	
	/**
	 * 通过异步方式缓存数据, 新数据将替换已有数据
	 * 当通过future.get()获取结果时, 当前线程才会被block
	 * @param <T> 保存数据的类型
	 * @param key 
	 * @param value
	 * @return 是否保存成功
	 */
	<T> Future<Boolean> asyncPut(final String key, final T value);
	/**
	 * 通过异步方式缓存数据, 新数据将替换已有数据
	 * 当通过future.get()获取结果时, 当前线程才会被block
	 * @param <T> 保存数据的类型
	 * @param key
	 * @param value
	 * @param expireTime 过期时间 ,单位 milliseconds
	 * @return 是否保存成功
	 */
	<T> Future<Boolean> asyncPut(final String key, final T value, long expireTime);
	
	/**
	 * 通过异步方式获取缓存对象
	 * @param <T>
	 * @param key
	 * @return 缓存的对象, 如果没有, 返回 null
	 */
	<T> Future<T> asyncGet(final String key);
	/**
	 * 通过异步方式批量获取结果
	 * @param <T> 返回结果的类型
	 * @param keys
	 * @return
	 */
	Future<Map<String, Object>> asyncGetBulk(final String... keys);
	/**
	 * 通过异步方式批量获取结果
	 * @param keys
	 * @return
	 */
	Future<Map<String, Object>> asyncGetBulk(final Collection<String> keys);
	/**
	 * 通过异步方式删除特定的缓存
	 * @param key
	 * @return
	 */
	Future<Boolean> asyncDelete(final String key);
	
}
