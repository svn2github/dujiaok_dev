package com.ssnn.dujiaok.model.product.detail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ssnn.dujiaok.model.product.ProductDetail;
import com.ssnn.dujiaok.util.DateUtil;
import com.ssnn.dujiaok.util.string.StringUtil;

public class HotelRoomDetail extends ProductDetail {
	private Integer id;
	private String hotelCode;
	private String hotelName;
	private Double price;
	private Date checkinDateBegin;
	private Date checkinDateEnd;
	private List<Date> checkinDates = new ArrayList<Date>();
	/**
	 * 面积.
	 */
	private String area;
	private String bedType;
	private String facilities;
	private List<String> pictureUrls;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHotelCode() {
		return hotelCode;
	}
	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getCheckinDateBegin() {
		return this.checkinDateBegin;
	}
	public void setCheckinDateBegin(Date checkinDateBegin) {
		this.checkinDateBegin = checkinDateBegin;
		if (this.checkinDates.size() == 0) {
			Date temp = new Date();
			if (!this.checkinDateBegin.before(temp)) {
				temp = (Date) this.checkinDateBegin.clone();
			}
			this.checkinDates.add(temp);
			return;
		}
		Date endDate = this.checkinDates.get(0);
		this.checkinDates.clear();
		DateUtil.fillDays(this.checkinDates, checkinDateBegin, endDate);
	}
	public Date getCheckinDateEnd() {
		return this.checkinDateEnd;
	}
	public void setCheckinDateEnd(Date checkinDateEnd) {
		this.checkinDateEnd = checkinDateEnd;
		if (this.checkinDates.size() == 0) {
			this.checkinDates.add(checkinDateEnd);
			return;
		}
		Date startDate = this.checkinDates.get(0);
		this.checkinDates.clear();
		DateUtil.fillDays(this.checkinDates, startDate, checkinDateEnd);
	}
	public List<Date> getCheckinDates() {
		return this.checkinDates;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFacilities() {
		return facilities;
	}
	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}
	public List<String> getPictureUrls() {
		return pictureUrls;
	}
	public void setPictureUrls(String pictureUrls) {
		if (StringUtil.isEmpty(pictureUrls)) {
			this.pictureUrls = new ArrayList<String>();
		} else {
			this.pictureUrls = Arrays.asList(pictureUrls.split(","));
		}
	}
	public String getBedType() {
		return bedType;
	}
	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	public void setCheckinDates(List<Date> checkinDates) {
		this.checkinDates = checkinDates;
	}
	
	@Override
	public Double getCheapestPrice() {
		return this.price;
	}
	
	@Override
	public int compareTo(ProductDetail o) {
		HotelRoomDetail temp = (HotelRoomDetail) o;
		return Double.compare(this.getCheapestPrice(), temp.getCheapestPrice());
	}
}
