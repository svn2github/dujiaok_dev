package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.page.condition.GlobalSearchCondition;
import com.ssnn.dujiaok.model.SearchDO;
import com.ssnn.dujiaok.util.enums.ProductEnums;

public interface SearchService {

	/**
	 * 整站搜索
	 * @param condition
	 * @param pagination
	 * @return
	 */
	QueryResult<SearchDO> globalSearch(GlobalSearchCondition condition , Pagination pagination) ;
	
	/**
	 * 单产品搜索
	 * @param condition
	 * @param pagination
	 * @return
	 */
	QueryResult<SearchDO> searchProduct(GlobalSearchCondition condition , Pagination pagination ,ProductEnums product) ;
}
