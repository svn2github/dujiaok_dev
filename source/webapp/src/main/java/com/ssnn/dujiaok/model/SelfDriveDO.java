package com.ssnn.dujiaok.model;

import java.util.Date;

/**
 * 自驾
 * @author langben 2012-1-18
 *
 */
public class SelfDriveDO extends AbstractProduct {
	
	/**
	 * 附加产品
	 */
	private String addProducts ;
	
	
	
	/**
	 * 游玩天数
	 */
	private int days ;
	
	/**
	 * 推荐指数
	 */
	private String recommend ;
	
	
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
	
	
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
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
