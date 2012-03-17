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
		if(StringUtils.isNotBlank(condition.getPlace())){
			map.put("place", condition.getPlace()) ;
		}
		ProductEnums e = null ;
		if(StringUtils.isNotBlank(condition.getProduct())){
			e = ProductEnums.fromValue(condition.getProduct()) ;
		}
		if(e == ProductEnums.HOTEL){
			e = null ; //暂时不支持酒店搜索
		}
		ProductEnums[] products = null ;
		if(e != null){
			products = new ProductEnums[]{e} ;
		}else{
			products = new ProductEnums[]{/*ProductEnums.HOTEL,*/ProductEnums.HOTEL_ROOM , ProductEnums.SELFDRIVE , ProductEnums.TICKET} ;
		}
		int count = searchDAO.countGlobalSearch(map , products) ;
		List<SearchDO> list = searchDAO.globalSearch(map, pagination , products);
		pagination.setTotalCount(count) ;
		return new QueryResult<SearchDO>(list, pagination);
	}


}
