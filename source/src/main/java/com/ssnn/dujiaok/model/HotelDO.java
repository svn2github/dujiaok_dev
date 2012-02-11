package com.ssnn.dujiaok.model;

import java.util.Date;
import java.util.List;

/**
 * 酒店HOTEL主表
 * @author shenjia.caosj 2012-1-17
 *
 */
public class HotelDO extends AbstractProduct {
	
	
	/**
	 * 酒店星级
	 */
	private int starRate ;
	
	/**
	 * 房间数量
	 */
	private int roomAmount ; 
	
	/**
	 * 简介
	 */
	private String introduction ;
	
	/**
	 * 备注
	 */
	private String memo ;
	
	private Date gmtModified ;
	
	private Date gmtCreate ;
	
	private List<HotelRoomDO> rooms ;
	
	
	public List<HotelRoomDO> getRooms() {
		return rooms;
	}

	public void setRooms(List<HotelRoomDO> rooms) {
		this.rooms = rooms;
	}

	public int getStarRate() {
		return starRate;
	}

	public void setStarRate(int starRate) {
		this.starRate = starRate;
	}

	public int getRoomAmount() {
		return roomAmount;
	}

	public void setRoomAmount(int roomAmount) {
		this.roomAmount = roomAmount;
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

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
}
