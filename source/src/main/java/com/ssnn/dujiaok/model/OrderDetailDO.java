package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author shenjia.caosj 2012-2-7
 *
 */
public class OrderDetailDO {

	private int id ;
	
	private String orderId ;
	
	/**
	 * 自驾为单人价格，其他均为价格
	 */
	private BigDecimal singlePrice ;
	
	private BigDecimal doublePrice ;
	
	private BigDecimal childPrice ;
	
	private BigDecimal insurePrice ;
	
	private int insureCount ;
	
	private String alipayId ;
	
	private Date gmtCreate ;

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(BigDecimal singlePrice) {
		this.singlePrice = singlePrice;
	}

	public BigDecimal getDoublePrice() {
		return doublePrice;
	}

	public void setDoublePrice(BigDecimal doublePrice) {
		this.doublePrice = doublePrice;
	}

	public BigDecimal getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(BigDecimal childPrice) {
		this.childPrice = childPrice;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	 
	public BigDecimal getInsurePrice() {
		return insurePrice;
	}

	public void setInsurePrice(BigDecimal insurePrice) {
		this.insurePrice = insurePrice;
	}

	public int getInsureCount() {
		return insureCount;
	}

	public void setInsureCount(int insureCount) {
		this.insureCount = insureCount;
	}
	
	
}
