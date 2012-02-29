package com.ssnn.dujiaok.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.dal.SearchDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.page.condition.GlobalSearchCondition;
import com.ssnn.dujiaok.biz.service.SearchService;
import com.ssnn.dujiaok.model.SearchDO;
import com.ssnn.dujiaok.util.enums.ProductEnums;

public class SearchServiceImpl implements SearchService {

	private SearchDAO searchDAO;

	public void setSearchDAO(SearchDAO searchDAO) {
		this.searchDAO = searchDAO;
	}

	@Override
	public QueryResult<SearchDO> globalSearch(GlobalSearchCondition condition , Pagination pagination ) {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("name", condition.getName()) ;
		int count = searchDAO.countGlobalSearch(map ,
				new ProductEnums[]{ProductEnums.HOTEL,ProductEnums.HOTEL_ROOM , ProductEnums.SELFDRIVE , ProductEnums.TICKET}) ;
		List<SearchDO> list = searchDAO.globalSearch(map, pagination , 
				new ProductEnums[]{ProductEnums.HOTEL,ProductEnums.HOTEL_ROOM , ProductEnums.SELFDRIVE , ProductEnums.TICKET});
		pagination.setTotalCount(count) ;
		return new QueryResult<SearchDO>(list, pagination);
	}

	@Override
	public QueryResult<SearchDO> searchProduct(GlobalSearchCondition condition, Pagination pagination ,ProductEnums product) {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("name", condition.getName()) ;
		int count = searchDAO.countGlobalSearch(map ,
				new ProductEnums[]{product}) ;
		List<SearchDO> list = searchDAO.globalSearch(map, pagination , 
				new ProductEnums[]{product});
		pagination.setTotalCount(count) ;
		return new QueryResult<SearchDO>(list, pagination);
	}

}
