package com.ssnn.dujiaok.web.action.search;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.page.condition.GlobalSearchCondition;
import com.ssnn.dujiaok.biz.service.SearchService;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.model.SearchDO;
import com.ssnn.dujiaok.util.enums.ProductEnums;
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
	 * 地方
	 */
	private String place;

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
			result = searchService.globalSearch(condition, pagination);

			if (CollectionUtils.isNotEmpty(result.getItems())) {
				for (SearchDO item : result.getItems()) {
					String productId = item.getProductId();
					if (StringUtils.startsWith(productId,
							ProductEnums.HOTEL.getName())) {
						continue;
					}
				}
			}
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

}
