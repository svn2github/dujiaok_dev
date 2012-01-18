package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 自驾detail
 * @author shenjia.caosj 2012-1-18
 *
 */
public class SelfDriveDetailDO extends AbstractProductDetail{

	/**
	 * 自驾ID
	 */
	private String selfDriveId ;
	
	private Date gmtCreate ;
	/**
	 * 双人价
	 */
	private BigDecimal doublePrice ;
	
	/**
	 * 单人价
	 */
	private BigDecimal singlePrice ;
	
	/**
	 * 儿童价
	 */
	private BigDecimal childPrice ;

	public String getSelfDriveId() {
		return selfDriveId;
	}

	public void setSelfDriveId(String selfDriveId) {
		this.selfDriveId = selfDriveId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public BigDecimal getDoublePrice() {
		return doublePrice;
	}

	public void setDoublePrice(BigDecimal doublePrice) {
		this.doublePrice = doublePrice;
	}

	public BigDecimal getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(BigDecimal singlePrice) {
		this.singlePrice = singlePrice;
	}

	public BigDecimal getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(BigDecimal childPrice) {
		this.childPrice = childPrice;
	}
	
	
	
}
