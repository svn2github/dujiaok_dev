package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Ticket 门票主表
 * @author shenjia.caosj 2012-1-9
 *
 */
public class TicketDO extends AbstractProduct {

	
	/**
	 * 根据规则产生的唯一ID
	 */
	private String ticketId ;
	
	
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice ;
	
	
	/**
	 * 图片
	 */
	private String images ;
	/**
	 * 支付方式
	 */
	private String payTypes ;
	/**
	 * 产品类别
	 */
	private String productTypes ;
	/**
	 * 产品简介
	 */
	private String introduction ;
	/**
	 * 注意事项
	 */
	private String notice ;
	/**
	 * 费用说明
	 */
	private String feeDesc ;
	/**
	 * 备注
	 */
	private String memo ;
	
	private Date gmtCreate ;
	
	private Date gmtModified ;
	
	private Date gmtExpire ;
	
	private List<TicketDetailDO> ticketDetails  ;

	public List<TicketDetailDO> getTicketDetails() {
		return ticketDetails;
	}

	public void setTicketDetails(List<TicketDetailDO> ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(String payTypes) {
		this.payTypes = payTypes;
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

	public Date getGmtExpire() {
		return gmtExpire;
	}

	public void setGmtExpire(Date gmtExpire) {
		this.gmtExpire = gmtExpire;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE) ;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	
	
}
