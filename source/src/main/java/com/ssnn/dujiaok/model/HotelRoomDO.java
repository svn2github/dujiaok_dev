package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author shenjia.caosj 2012-1-18
 *
 */
public class HotelRoomDO {

	/**
	 * PK
	 */
	private int id ;
	
	/**
	 * 房间名
	 */
	private String name ;
	
	/**
	 * 根据规则生成的唯一房间编号
	 */
	private String roomId ;
	
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
	 * 
	 */
	private List<HotelRoomDetailDO> roomDetails ;

	
	public List<HotelRoomDetailDO> getRoomDetails() {
		return roomDetails;
	}

	public void setRoomDetails(List<HotelRoomDetailDO> roomDetails) {
		this.roomDetails = roomDetails;
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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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
	
}
