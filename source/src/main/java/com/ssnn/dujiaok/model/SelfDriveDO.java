package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 自驾
 * @author shenjia.caosj 2012-1-18
 *
 */
public class SelfDriveDO extends AbstractProduct {

	/**
	 * 根据规则生存的唯一ID
	 */
	private String selfDriveId ;
	
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice ;
	
	/**
	 * 游玩天数
	 */
	private int days ;
	
	/**
	 * 付款方式
	 */
	private String payTypes ;
	
	/**
	 * 产品类别
	 */
	private String productTypes ;
	
	/**
	 * 图片
	 */
	private String images ;
	
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
	
	private List<SelfDriveDetailDO> details ;
	
	public List<SelfDriveDetailDO> getDetails() {
		return details;
	}

	public void setDetails(List<SelfDriveDetailDO> details) {
		this.details = details;
	}

	public Date getGmtExpire() {
		return gmtExpire;
	}

	public void setGmtExpire(Date gmtExpire) {
		this.gmtExpire = gmtExpire;
	}

	public String getSelfDriveId() {
		return selfDriveId;
	}

	public void setSelfDriveId(String selfDriveId) {
		this.selfDriveId = selfDriveId;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
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

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}
	
	
}
