package com.ssnn.dujiaok.model;

import java.util.Date;

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
	 * 酒店ID
	 */
	private String hotelId ;
		
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
	
}
