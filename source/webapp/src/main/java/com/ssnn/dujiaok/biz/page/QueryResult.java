package com.ssnn.dujiaok.biz.page;

import java.util.List;

/**
 * 查询结果
 * @author langben 2012-1-12
 *
 */
public class QueryResult<T> {
	
	@SuppressWarnings("unused")
	private QueryResult(){}
	
	public QueryResult(List<T> items, Pagination pagination) {
		super();
		this.items = items;
		this.pagination = pagination;
	}
	/**
	 * 查询记录结果
	 */
	private List<T> items ;
	
	private Pagination pagination ;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
