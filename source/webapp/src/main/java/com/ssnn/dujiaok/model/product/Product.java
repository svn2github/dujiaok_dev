package com.ssnn.dujiaok.model.product;

import java.util.Date;

public class Product {
	private Integer id;

    private Date    gmtCreate;

    private Date    gmtModify;

    private Integer typeId;

    private String  name;

    private Double  marketPrice;

    private Integer tourDays;

    private Integer locationCode;

    private String  dest;

    private String  post;

    private String  payType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getTourDays() {
		return tourDays;
	}

	public void setTourDays(Integer tourDays) {
		this.tourDays = tourDays;
	}

	public Integer getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(Integer locationCode) {
		this.locationCode = locationCode;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}
