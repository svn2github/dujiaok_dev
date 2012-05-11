package com.ssnn.dujiaok.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
		map.put("place", condition.getPlace()) ;
		map.put("starRate", condition.getStarRate());
		map.put("productType", condition.getProductType()) ;
		map.put("days", condition.getDays()) ;
		
		ProductEnums e = ProductEnums.fromValue(condition.getProduct()) ;
		ProductEnums[] products = null ; 
		if(e != ProductEnums.UNKNOWN){
			products = new ProductEnums[]{e} ;
		}else{
			products = new ProductEnums[]{/*ProductEnums.HOTEL,*/ProductEnums.HOTEL , ProductEnums.SELFDRIVE , ProductEnums.TICKET} ;
		}

		map.put("order", condition.getOrder()) ;
		map.put("orderSeq", condition.getOrderSeq()) ;
		//价格
		map.put("startPrice", condition.getStartPrice()) ;
		map.put("endPrice", condition.getEndPrice()) ;
		
		int count = searchDAO.countGlobalSearch(map , products) ;
		List<SearchDO> list = searchDAO.globalSearch(map, pagination , products);
		pagination.setTotalCount(count) ;
		return new QueryResult<SearchDO>(list, pagination);
	}


}

