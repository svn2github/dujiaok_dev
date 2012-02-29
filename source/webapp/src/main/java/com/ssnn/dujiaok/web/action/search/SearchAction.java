package com.ssnn.dujiaok.web.action.search;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.page.condition.GlobalSearchCondition;
import com.ssnn.dujiaok.biz.service.SearchService;
import com.ssnn.dujiaok.model.SearchDO;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author shenjia.caosj 2012-2-29
 *
 */
@SuppressWarnings("serial")
public class SearchAction extends BasicAction implements ModelDriven<Pagination>{

	private String keyword ;
	
	private QueryResult<SearchDO> result ;
	
	private SearchService searchService ;
	
	private Pagination pagination = new Pagination(1) ;
	
	@Override
	public String execute() throws Exception {
		
		if(!StringUtils.isBlank(keyword)){
			GlobalSearchCondition condition = new GlobalSearchCondition() ;
			condition.setName(keyword) ;
			result = searchService.globalSearch(condition, pagination) ;
		}
		
		return SUCCESS ;
	}
	
	/**
	 * ------------------------------------------------------------------------
	 */
	
	

	public String getKeyword() {
		return keyword;
	}

	public QueryResult<SearchDO> getResult() {
		return result;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public Pagination getModel() {
		return pagination ;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	
}
