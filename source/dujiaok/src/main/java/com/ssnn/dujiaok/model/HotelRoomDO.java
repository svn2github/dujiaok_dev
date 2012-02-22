package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author shenjia.caosj 2012-1-18
 *
 */
public class HotelRoomDO extends AbstractProduct{

	/**
	 * PK
	 */
	private int id ;
	
	/**
	 * 房间名
	 */
	private String name ;
	
	/**
	 * 酒店ID
	 */
	private String hotelId ;
	
	/**
	 * 支付方式
	 */
	private String payTypes ;
	
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice ;
	
	/**
	 * 床型
	 */
	private String bed ;
	
	/**
	 * 面积
	 */
	private String roomArea ;
	
	/**
	 * 配套
	 */
	private String roomFacilities ;
	
	/**
	 * 介绍
	 */
	private String introduction ;
	
	/**
	 * 房型图片
	 */
	private String categoryImage ;
	
	/**
	 * 备注
	 */
	private String memo ;
	
	private Date gmtCreate ;
	
	private Date gmtModified ;
	
	private List<ProductDetailDO> details;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(String payTypes) {
		this.payTypes = payTypes;
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

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getRoomArea() {
		return roomArea;
	}

	public void setRoomArea(String roomArea) {
		this.roomArea = roomArea;
	}

	public String getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(String roomFacilities) {
		this.roomFacilities = roomFacilities;
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

	public List<ProductDetailDO> getDetails() {
		return details;
	}

	public void setDetails(List<ProductDetailDO> details) {
		this.details = details;
		if (this.details == null || this.details.size() == 0) {
			setCheapestPrice(new BigDecimal("-1"));
		} else {
			setCheapestPrice(Collections.min(details).getCheapestPrice());
		}
	}
	
}