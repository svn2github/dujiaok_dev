package com.ssnn.dujiaok.biz.dal;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.SearchDO;
import com.ssnn.dujiaok.util.enums.ProductEnums;

/**
 * 搜索
 * @author langben 2012-2-29
 *
 */
public interface SearchDAO {

	List<SearchDO> globalSearch(Map<String,Object> condition , Pagination pagination , ProductEnums[] searchRegions) ;

	int countGlobalSearch(Map<String, Object> condition ,ProductEnums[] searchRegions)  ;
}
