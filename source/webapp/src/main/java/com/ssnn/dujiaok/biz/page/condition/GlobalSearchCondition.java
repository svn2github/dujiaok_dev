package com.ssnn.dujiaok.biz.page.condition;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

public class GlobalSearchCondition {

	private String name ;
	
	private String product ;
	
	private String place ;
	
	private Integer starRate ;
	
	private String order ;
	
	private String orderSeq ;
	
	private String productType ;
	
	/**
	 * 1,200
	 */
	private BigDecimal startPrice ;
	
	private BigDecimal endPrice ;
	
	private Integer days ;

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getName() {
		return StringUtils.trim(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStarRate() {
		return starRate;
	}

	public void setStarRate(Integer starRate) {
		this.starRate = starRate;
	}

	public String getProduct() {
		return product;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public BigDecimal getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(BigDecimal endPrice) {
		this.endPrice = endPrice;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	
	
	
}
