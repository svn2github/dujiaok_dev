package com.ssnn.dujiaok.model;

import java.util.Date;

/**
 * Ticket 门票主表
 * 
 * @author langben 2012-1-9
 * 
 */
public class TicketDO extends AbstractProduct {

	/**
	 * 推荐指数
	 */
	private String recommend;

	/**
	 * 门票类型（电子、短信等）
	 */
	private String ticketType;
	
	/**
	 * 产品类别
	 */
	private String productTypes;
	/**
	 * 产品简介
	 */
	private String introduction;
	/**
	 * 注意事项
	 */
	private String notice;
	/**
	 * 费用说明
	 */
	private String feeDesc;
	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 不打折为‘T’
	 */
	private String notDiscout;

	private Date gmtCreate;

	private Date gmtModified;

	public String getNotDiscout() {
		return notDiscout;
	}

	public void setNotDiscout(String notDiscout) {
		this.notDiscout = notDiscout;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getFeeDesc() {
		return feeDesc;
	}

	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc;
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

}
