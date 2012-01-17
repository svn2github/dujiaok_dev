package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author shenjia.caosj 2012-1-18
 *
 */
public class HotelRoomDetailDO extends AbstractProductDetail {

	/**
	 * roomID
	 */
	private String roomId ;
	
	/**
	 * 价格
	 */
	private BigDecimal price ;
	
	private Date gmtCreate ;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	
	
}
