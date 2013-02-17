package com.ssnn.dujiaok.biz.page;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 分页Bean
 * @author langben 2012-1-12
 *
 */
public class Pagination {
	
	/**
	 * 默认每页20
	 */
	private static final int DEFAULT_SIZE = 20 ;
	
	@SuppressWarnings("unused")
	private Pagination(){
		
	}
	
	/**
	 * 
	 * @param start 开始记录
	 * @param size 每页记录数
	 */
	public Pagination(int start, int size) {
		this.start = start;
		this.size = size;
	}
	
	public Pagination(int start){
		this(start,DEFAULT_SIZE) ;
	}

	/**
	 * 开始记录数,start from 1
	 */
	protected int start ;
	
	/**
	 * 一次取出记录条数
	 */
	protected int size ;
	
	protected int totalCount ;
	
	public int getTotalPages(){
		return (totalCount+size-1) / size ;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE) ;
	}
}
