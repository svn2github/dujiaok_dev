package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.SearchDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.SearchDO;
import com.ssnn.dujiaok.util.IntegerUtils;
import com.ssnn.dujiaok.util.enums.ProductEnums;

public class IBatisSearchDAO extends SqlMapClientDaoSupport implements SearchDAO{

	@Override
	public List<SearchDO> globalSearch(Map<String, Object> condition, Pagination pagination , ProductEnums[] searchRegions) {
		condition.put("start", pagination.getStart() - 1) ;
		condition.put("size", pagination.getSize()) ;
		
		for(ProductEnums region : searchRegions){
			condition.put(region.getName(), true) ;
		}
		
		return getSqlMapClientTemplate().queryForList("search.globalSearch" , condition) ;
	}

	
	@Override
	public int countGlobalSearch(Map<String, Object> condition ,ProductEnums[] searchRegions) {
		for(ProductEnums region : searchRegions){
			condition.put(region.getName(), true) ;
		}
		return IntegerUtils.objectToInt(getSqlMapClientTemplate().queryForObject("search.countGlobalSearch" , condition)) ;
	}
	
}

