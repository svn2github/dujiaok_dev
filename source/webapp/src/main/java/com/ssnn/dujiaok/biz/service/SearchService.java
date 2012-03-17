package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.page.condition.GlobalSearchCondition;
import com.ssnn.dujiaok.model.SearchDO;

public interface SearchService {

	/**
	 * 整站搜索
	 * @param condition
	 * @param pagination
	 * @return
	 */
	QueryResult<SearchDO> globalSearch(GlobalSearchCondition condition , Pagination pagination) ;
	
}
