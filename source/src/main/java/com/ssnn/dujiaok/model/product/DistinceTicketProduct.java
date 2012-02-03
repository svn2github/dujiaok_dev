package com.ssnn.dujiaok.model.product;

import java.util.Date;
import java.util.List;

public class DistinceTicketProduct {
	private Integer id;
	private String name;
	private Date beginDate;
	private Date endDate;
	private Double price;
	private Double marketPrice;
	private List<String> payTypes;
	private List<String> additionProducts;
	private Integer days;
	private List<String> type;
	private String area;
	private String areaDesc;
	private List<String> pictures;
	private String desc;
	private String noteDesc;
	private String feeDesc;
	private String remarks;
	
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public List<String> getPayTypes() {
		return payTypes;
	}
	public void setPayTypes(List<String> payTypes) {
		this.payTypes = payTypes;
	}
	public List<String> getAdditionProducts() {
		return additionProducts;
	}
	public void setAdditionProducts(List<String> additionProducts) {
		this.additionProducts = additionProducts;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public List<String> getPictures() {
		return pictures;
	}
	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getNoteDesc() {
		return noteDesc;
	}
	public void setNoteDesc(String noteDesc) {
		this.noteDesc = noteDesc;
	}
	public String getFeeDesc() {
		return feeDesc;
	}
	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
