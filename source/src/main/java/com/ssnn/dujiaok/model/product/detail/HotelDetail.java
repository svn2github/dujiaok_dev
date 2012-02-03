package com.ssnn.dujiaok.model.product.detail;

import com.ssnn.dujiaok.model.product.ProductDetail;

public class HotelDetail extends ProductDetail {
	private Integer id;
	private String name;
	private String province;
	private String city;
	private String zone;
	private String location;
	private String starLevel;
	private Integer roomNum;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	@Override
	public Double getCheapestPrice() {
		return 0.0;
	}
	
	@Override
	public int compareTo(ProductDetail o) {
		return 0;
	}
}
