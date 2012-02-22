package com.ssnn.dujiaok.model.order;

import java.util.Date;

public class Order {
	private Integer id;

    private Date    gmtCreate;

    private Date    gmtModify;

    private Integer userId;

    private Integer productId;

    private Integer tourId;

    private Integer typeId;

    private String  payStatus;
    private String  status;

    private Integer singleNumber;

    private Double  singlePrice;

    private Integer doubleNumber;

    private Double  doublePrice;

    private Integer childNumber;

    private Double  childPrice;

    private Integer comboNumber;

    private Double  comboPrice;

    private Double  totalPrice;

    private String  comment;

    private Integer insure;

    private String  personInfo;

    private String  alipayId;

    private String  alipayStatus;

    private String  startDate;

    private Integer adultNumber;

    private Integer hotelNumber;

    private Double  hotelPrice;

    private Integer hotelDay;

    private String  endDate;

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getTourId() {
		return tourId;
	}

	public void setTourId(Integer tourId) {
		this.tourId = tourId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSingleNumber() {
		return singleNumber;
	}

	public void setSingleNumber(Integer singleNumber) {
		this.singleNumber = singleNumber;
	}

	public Double getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(Double singlePrice) {
		this.singlePrice = singlePrice;
	}

	public Integer getDoubleNumber() {
		return doubleNumber;
	}

	public void setDoubleNumber(Integer doubleNumber) {
		this.doubleNumber = doubleNumber;
	}

	public Double getDoublePrice() {
		return doublePrice;
	}

	public void setDoublePrice(Double doublePrice) {
		this.doublePrice = doublePrice;
	}

	public Integer getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}

	public Double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}

	public Integer getComboNumber() {
		return comboNumber;
	}

	public void setComboNumber(Integer comboNumber) {
		this.comboNumber = comboNumber;
	}

	public Double getComboPrice() {
		return comboPrice;
	}

	public void setComboPrice(Double comboPrice) {
		this.comboPrice = comboPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getInsure() {
		return insure;
	}

	public void setInsure(Integer insure) {
		this.insure = insure;
	}

	public String getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public String getAlipayStatus() {
		return alipayStatus;
	}

	public void setAlipayStatus(String alipayStatus) {
		this.alipayStatus = alipayStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getAdultNumber() {
		return adultNumber;
	}

	public void setAdultNumber(Integer adultNumber) {
		this.adultNumber = adultNumber;
	}

	public Integer getHotelNumber() {
		return hotelNumber;
	}

	public void setHotelNumber(Integer hotelNumber) {
		this.hotelNumber = hotelNumber;
	}

	public Double getHotelPrice() {
		return hotelPrice;
	}

	public void setHotelPrice(Double hotelPrice) {
		this.hotelPrice = hotelPrice;
	}

	public Integer getHotelDay() {
		return hotelDay;
	}

	public void setHotelDay(Integer hotelDay) {
		this.hotelDay = hotelDay;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
