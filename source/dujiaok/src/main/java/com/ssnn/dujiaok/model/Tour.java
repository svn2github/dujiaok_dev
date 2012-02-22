package com.ssnn.dujiaok.model;

import java.util.Date;

public class Tour {

    private Integer id;

    private Date    gmtCreate;

    private Date    gmtModify;

    private Integer productId;

    private String  startDate;

    private Double  singlePrice;

    private Double  doublePrice;

    private Double  childPrice;
    
    private Double  comboPrice;
    
    private Double  hotelPrice;

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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(Double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public Double getDoublePrice() {
        return doublePrice;
    }

    public void setDoublePrice(Double doublePrice) {
        this.doublePrice = doublePrice;
    }

    public Double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(Double childPrice) {
        this.childPrice = childPrice;
    }

	public Double getComboPrice() {
		return comboPrice;
	}

	public void setComboPrice(Double comboPrice) {
		this.comboPrice = comboPrice;
	}

    public Double getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(Double hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

}