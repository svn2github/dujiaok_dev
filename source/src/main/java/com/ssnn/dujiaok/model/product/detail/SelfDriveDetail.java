package com.ssnn.dujiaok.model.product.detail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ssnn.dujiaok.model.product.ProductDetail;
import com.ssnn.dujiaok.util.DateUtil;

public class SelfDriveDetail extends ProductDetail {
	private Integer id;
	private Double singlePrice;
	private Double doublePrice;
	private Double childPrice;
	private Date departDateBegin;
	private Date departDateEnd;
	private List<Date> departDates = new ArrayList<Date>();
	private Integer jouneyDayNum;
	/**
	 * 行程.
	 */
	private String journeyDesc;
	/**
	 * 省.
	 */
	private String destProvince;
	/**
	 * 市.
	 */
	private String destCity;
	/**
	 * 区.
	 */
	private String destZone;
	/**
	 * 详细地址.
	 */
	private String destLocation;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getDepartDateBegin() {
		return departDateBegin;
	}
	public void setDepartDateBegin(Date departDateBegin) {
		this.departDateBegin = departDateBegin;
		if (departDates.size() == 0) {
			Date temp = new Date();
			if (!this.departDateBegin.before(temp)) {
				temp = (Date) this.departDateBegin.clone();
			}
			departDates.add(temp);
			return;
		}
		Date endDate = departDates.get(0);
		departDates.clear();
		DateUtil.fillDays(departDates, departDateBegin, endDate);
	}
	public Date getDepartDateEnd() {
		return departDateEnd;
	}
	public void setDepartDateEnd(Date departDateEnd) {
		this.departDateEnd = departDateEnd;
		if (departDates.size() == 0) {
			departDates.add(departDateEnd);
			return;
		}
		Date startDate = departDates.get(0);
		departDates.clear();
		DateUtil.fillDays(departDates, startDate, departDateEnd);
	}
	public List<Date> getDepartDates() {
		return departDates;
	}
	
	public String getJourneyDesc() {
		return journeyDesc;
	}
	public void setJourneyDesc(String journeyDesc) {
		this.journeyDesc = journeyDesc;
	}
	public String getDestProvince() {
		return destProvince;
	}
	public void setDestProvince(String destProvince) {
		this.destProvince = destProvince;
	}
	public String getDestCity() {
		return destCity;
	}
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
	public String getDestZone() {
		return destZone;
	}
	public void setDestZone(String destZone) {
		this.destZone = destZone;
	}
	public String getDestLocation() {
		return destLocation;
	}
	public void setDestLocation(String destLocation) {
		this.destLocation = destLocation;
	}
	public Integer getJouneyDayNum() {
		return jouneyDayNum;
	}
	public void setJouneyDayNum(Integer jouneyDayNum) {
		this.jouneyDayNum = jouneyDayNum;
	}
	@Override
	public Double getCheapestPrice() {
		return Math.min(this.singlePrice, this.doublePrice/2);
	}
	
	@Override
	public int compareTo(ProductDetail o) {
		SelfDriveDetail temp = (SelfDriveDetail) o;
		return Double.compare(this.getCheapestPrice(), temp.getCheapestPrice());
	}
	
	public static void main(String[] args) {
		SelfDriveDetail detail = new SelfDriveDetail();
		detail.setDepartDateBegin(new Date("2012/02/1"));
		detail.setDepartDateEnd(new Date("2012/02/21"));
		System.out.println(detail.getDepartDates());
	}
}
