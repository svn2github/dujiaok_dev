package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * @author shenjia.caosj 2012-2-2
 *
 */
public class OrderDO {

	/**
	 * pk
	 */
	private int id ;
	
	/**
	 * 规则生成的ID
	 */
	private String orderId ;
	
	/**
	 * 会员
	 */
	private String memberId ;
	
	/**
	 * 名称
	 */
	private String name ;
	
	/**
	 * 订购产品类型
	 */
	private String productType ;
	/**
	 * 订购产品ID
	 */
	private String productId ;
	
	/**
	 * 订单总价
	 */
	private BigDecimal price ;
	
	/**
	 * 酒店为x晚，门票为x张，自驾为x成人
	 */
	private int count ;
	
	
	/**
	 * 门票为儿童x张
	 */
	private int secondaryCount;
	
	/**
	 * 订单状态  等待付款，付款成功，关闭，退款
	 */
	private String orderStatus ;
	
	/**
	 * 付款状态：已经付款，未付款
	 */
	private String payStatus ;
	
	private Date gmtCreate ;
	
	private Date gmtModified ;

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSecondaryCount() {
		return secondaryCount;
	}

	public void setSecondaryCount(int secondaryCount) {
		this.secondaryCount = secondaryCount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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
