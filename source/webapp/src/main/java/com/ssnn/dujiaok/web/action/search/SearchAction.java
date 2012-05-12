package com.ssnn.dujiaok.web.action.search;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.BO.FrontViewBO;
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
public class SearchAction extends BasicAction {

	/**
	 * 关键字
	 */
	private String keyword;

	/**
	 * 产品
	 */
	private String s_product;
	
	/**
	 * 出游天数
	 */
	private Integer s_days ;
	
	/**
	 * 价格 1,200 表示1-200 ， 
	 */
	private String s_sellPrice ;

	/**
	 * 地方
	 */
	private String s_city;
	
	/**
	 * 酒店星级
	 */
	private Integer s_starRate ;
	
	/**
	 * 
	 */
	private String s_productType ;
	
	/**
	 * order字段 ， 价格|null
	 */
	private String s_order ;
	
	/**
	 * order 顺序  desc asc
	 */
	private String s_orderSeq ;

	private QueryResult<SearchDO> result;

	private SearchService searchService;

	private FrontViewBO              frontViewBO;
	
	private int page = 1;
	
	private int size = 20 ;
	
	private int start = 1 ;
	

	@Override
	public String execute() throws Exception {
		
		GlobalSearchCondition condition = new GlobalSearchCondition();
		condition.setName(keyword);
		condition.setPlace(s_city);
		condition.setProduct(s_product);
		condition.setOrder(s_order) ;
		condition.setDays(s_days) ;
		condition.setStarRate(s_starRate) ;
		condition.setProductType(s_productType) ;
		
		condition.setOrderSeq(StringUtils.equals(s_orderSeq, "desc") ?  "desc" : "asc") ;
		if(StringUtils.isNotBlank(s_sellPrice)){
			String[] s = StringUtils.split(s_sellPrice , "-") ;
			if(s.length == 2){
				String sStartPrice = s[0] ;
				String sEndPrice = s[1] ;
				if(StringUtils.isNumeric(sStartPrice) && StringUtils.isNumeric(sEndPrice)){
					condition.setStartPrice(new BigDecimal(sStartPrice)) ;
					condition.setEndPrice(new BigDecimal(sEndPrice)) ;
				}
			}
		}
		
		Pagination pagination = new Pagination((page-1)*size + 1);
		
		result = searchService.globalSearch(condition, pagination);
		
		return SUCCESS;
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

	public String getS_city() {
		return s_city;
	}

	public void setS_city(String s_city) {
		this.s_city = s_city;
	}

	public String getS_sellPrice() {
		return s_sellPrice;
	}

	public void setS_sellPrice(String s_sellPrice) {
		this.s_sellPrice = s_sellPrice;
	}

	public String getS_product() {
		return s_product;
	}

	public void setS_product(String s_product) {
		this.s_product = s_product;
	}

	public Integer getS_days() {
		return s_days;
	}

	public void setS_days(Integer s_days) {
		this.s_days = s_days;
	}

	public String getS_order() {
		return s_order;
	}

	public void setS_order(String s_order) {
		this.s_order = s_order;
	}

	public String getS_orderSeq() {
		return s_orderSeq;
	}

	public void setS_orderSeq(String s_orderSeq) {
		this.s_orderSeq = s_orderSeq;
	}

    public String getS_productType() {
		return s_productType;
	}

	public void setS_productType(String s_productType) {
		this.s_productType = s_productType;
	}

	public void setFrontViewBO(FrontViewBO frontViewBO) {
        this.frontViewBO = frontViewBO;
    }
    
    public FrontViewBO getFrontViewBO() {
        return frontViewBO;
    }

	public Integer getS_starRate() {
		return s_starRate;
	}

	public void setS_starRate(Integer s_starRate) {
		this.s_starRate = s_starRate;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
