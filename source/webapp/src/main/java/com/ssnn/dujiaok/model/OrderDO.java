package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单
 * 
 * @author shenjia.caosj 2012-2-2
 * 
 */
public class OrderDO {

	/**
	 * pk
	 */
	private int id;

	/**
	 * 规则生成的ID
	 */
	private String orderId;

	/**
	 * 会员
	 */
	private String memberId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 酒店为x晚，门票为x张，自驾为x成人
	 */
	private int count;

	/**
	 * 门票为儿童x张
	 */
	private int secondaryCount;

	/**
	 * 订购产品类型
	 */
	private String productType;
	/**
	 * 订购产品ID
	 */
	private String productId;
	/**
	 * 
	 */
	private String productDetailId;

	/**
	 * 订单总价
	 */
	private BigDecimal price;

	/**
	 * 订单状态 等待付款，付款成功，关闭，退款 OrderStatusEnums
	 */
	private String status;

	/**
	 * 状态详细说明（如订单关闭原因）
	 */
	private String statusDetail;

	/**
	 * 付款方式
	 */
	private String payType;
	/**
	 * 付款状态：已经付款，未付款 PayStatusEnums
	 */
	private String payStatus;

	/**
	 * 支付宝ID
	 */
	private String alipayId;

	/**
	 * 备注
	 */
	private String memo;

	private Date gmtCreate;

	private Date gmtOrderStart;

	private Date gmtOrderEnd;

	private Date gmtModified;
	private int insureNum;
	/**
	 * 付款时间
	 */
	private Date gmtPaid;

	private List<OrderContactDO> contacts;

	public List<OrderContactDO> getContacts() {
		return contacts;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public void setContacts(List<OrderContactDO> contacts) {
		this.contacts = contacts;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPayType() {
		return payType;
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

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getGmtPaid() {
		return gmtPaid;
	}

	public void setGmtPaid(Date gmtPaid) {
		this.gmtPaid = gmtPaid;
	}

	public Date getGmtOrderStart() {
		return gmtOrderStart;
	}

	public void setGmtOrderStart(Date gmtOrderStart) {
		this.gmtOrderStart = gmtOrderStart;
	}

	public Date getGmtOrderEnd() {
		return gmtOrderEnd;
	}

	public void setGmtOrderEnd(Date gmtOrderEnd) {
		this.gmtOrderEnd = gmtOrderEnd;
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

	public String getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(String productDetailId) {
		this.productDetailId = productDetailId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDetail() {
		return statusDetail;
	}

	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
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

	public int getInsureNum() {
		return insureNum;
	}

	public void setInsureNum(int insureNum) {
		this.insureNum = insureNum;
	}
}
