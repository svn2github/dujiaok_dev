package com.ssnn.dujiaok.web.action.search;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.page.condition.GlobalSearchCondition;
import com.ssnn.dujiaok.biz.service.SearchService;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.model.SearchDO;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author shenjia.caosj 2012-2-29
 * 
 */
@SuppressWarnings("serial")
public class SearchAction extends BasicAction implements
		ModelDriven<Pagination> {

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
	private int s_days ;
	
	/**
	 * 价格 1,200 表示1-200 ， 
	 */
	private String s_sellPrice ;

	/**
	 * 地方
	 */
	private String place;
	
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

	private ProductDetailService productDetailService;

	private Pagination pagination = new Pagination(1);

	@Override
	public String execute() throws Exception {

		if (!StringUtils.isBlank(keyword)) {
			GlobalSearchCondition condition = new GlobalSearchCondition();
			condition.setName(keyword);
			condition.setPlace(place);
			condition.setProduct(s_product);
			condition.setOrder(s_order) ;
			condition.setOrderSeq(StringUtils.equals(s_orderSeq, "desc") ?  "desc" : "asc") ;
			if(StringUtils.isNotBlank(s_sellPrice)){
				String[] s = StringUtils.split(s_sellPrice , ",") ;
				if(s.length == 2){
					String sStartPrice = s[0] ;
					String sEndPrice = s[1] ;
					if(StringUtils.isNumeric(sStartPrice) && StringUtils.isNumeric(sEndPrice)){
						condition.setStartPrice(new BigDecimal(sStartPrice)) ;
						condition.setEndPrice(new BigDecimal(sEndPrice)) ;
					}
				}
			}
			result = searchService.globalSearch(condition, pagination);

		}

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

	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
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

	public int getS_days() {
		return s_days;
	}

	public void setS_days(int s_days) {
		this.s_days = s_days;
	}

	@Override
	public Pagination getModel() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setProductDetailService(
			ProductDetailService productDetailService) {
		this.productDetailService = productDetailService;
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

}
