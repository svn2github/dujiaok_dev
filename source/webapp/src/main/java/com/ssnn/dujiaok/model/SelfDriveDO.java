package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 自驾
 * @author shenjia.caosj 2012-1-18
 *
 */
public class SelfDriveDO extends AbstractProduct {
	
	/**
	 * 附加产品
	 */
	private String addProducts ;
	
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice ;
	
	/**
	 * 游玩天数
	 */
	private int days ;
	
	/**
	 * 推荐指数
	 */
	private String recommend ;
	
	/**
	 * 付款方式
	 */
	private String payTypes ;
	
	/**
	 * 产品类别
	 */
	private String productTypes ;
	
	/**
	 * 费用说明
	 */
	private String feeDesc ;
	
	/**
	 * 行程
	 */
	private String schedule ;
	
	/**
	 * 介绍
	 */
	private String introduction ;
	
	private String memo ;
	
	private Date gmtCreate ;
	
	private Date gmtModified ;
	
	/**
	 * 过期时间
	 */
	private Date gmtExpire ;
	
	private List<ProductDetailDO> details ;
	
	public List<ProductDetailDO> getDetails() {
		return details;
	}

	public void setDetails(List<ProductDetailDO> details) {
		this.details = details;
		if (this.details == null || this.details.size() == 0) {
			setCheapestPrice(new BigDecimal("-1"));
		} else {
			setCheapestPrice(Collections.min(this.details).getCheapestPrice());
		}
	}

	public Date getGmtExpire() {
		return gmtExpire;
	}

	public void setGmtExpire(Date gmtExpire) {
		this.gmtExpire = gmtExpire;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public BigDecimal getCheapestPrice() {
		return this.cheapestPrice.compareTo(new BigDecimal("-1")) == 0 ? this.marketPrice : this.cheapestPrice;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(String payTypes) {
		this.payTypes = payTypes;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getFeeDesc() {
		return feeDesc;
	}

	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}

	public String getAddProducts() {
		return addProducts;
	}

	public void setAddProducts(String addProducts) {
		this.addProducts = addProducts;
	}
	
	
}