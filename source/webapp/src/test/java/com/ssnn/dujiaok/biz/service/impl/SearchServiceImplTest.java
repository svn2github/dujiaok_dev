package com.ssnn.dujiaok.biz.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.page.condition.GlobalSearchCondition;
import com.ssnn.dujiaok.biz.service.SearchService;
import com.ssnn.dujiaok.model.SearchDO;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;

@ContextConfiguration(locations={
		"classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml",
		"classpath:bean/biz-datasource.xml" ,
		"classpath:bean/biz-service.xml" ,
	})
public class SearchServiceImplTest extends AbstractBaseJUnit4Test{

	@Autowired
	private SearchService searchService ;

	@Test
	public void test_glo(){
		GlobalSearchCondition condition = new GlobalSearchCondition() ;
		condition.setName("度假");
		QueryResult<SearchDO> r = searchService.globalSearch(condition,new Pagination(1) ) ;
		
		int a = 5 ;
	}
}

