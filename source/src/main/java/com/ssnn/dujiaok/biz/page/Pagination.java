package com.ssnn.dujiaok.biz.page;

/**
 * 分页Bean
 * @author shenjia.caosj 2012-1-12
 *
 */
public class Pagination {
	
	/**
	 * 默认每页20
	 */
	private static final int DEFAULT_SIZE = 20 ;
	
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

	private int start ;
	
	private int size ;

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
	
	
}