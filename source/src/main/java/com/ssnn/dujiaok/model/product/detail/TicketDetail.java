package com.ssnn.dujiaok.model.product.detail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ssnn.dujiaok.model.product.ProductDetail;
import com.ssnn.dujiaok.util.DateUtil;

public class TicketDetail extends ProductDetail {
	private Integer id;
	private Double price;
	private Date useDateBegin;
	private Date useDateEnd;
	private List<Date> useDates = new ArrayList<Date>();
	private String province;
	private String city;
	private String zone;
	/**
	 * 地址
	 */
	private String location;
	/**
	 * 注意事项.
	 */
	private String attentionDesc;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getUseDateBegin() {
		return this.useDateBegin;
	}
	public void setUseDateBegin(Date useDateBegin) {
		this.useDateBegin = useDateBegin;
		if (this.useDates.size() == 0) {
			Date temp = new Date();
			if (!this.useDateBegin.before(temp)) {
				temp = (Date) this.useDateBegin.clone();
			}
			this.useDates.add(temp);
			return;
		}
		Date endDate = this.useDates.get(0);
		this.useDates.clear();
		DateUtil.fillDays(this.useDates, useDateBegin, endDate);
	}
	public Date getUseDateEnd() {
		return this.useDateEnd;
	}
	public void setUseDateEnd(Date useDateEnd) {
		this.useDateEnd = useDateEnd;
		if (this.useDates.size() == 0) {
			this.useDates.add(useDateEnd);
			return;
		}
		Date startDate = this.useDates.get(0);
		this.useDates.clear();
		DateUtil.fillDays(this.useDates, startDate, useDateEnd);
	}
	public List<Date> getUseDates() {
		return this.useDates;
	}
	public String getAttentionDesc() {
		return attentionDesc;
	}
	public void setAttentionDesc(String attentionDesc) {
		this.attentionDesc = attentionDesc;
	}
	public String getLocation() {
		return this.location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	
	@Override
	public Double getCheapestPrice() {
		return this.price;
	}
	@Override
	public int compareTo(ProductDetail o) {
		TicketDetail temp = (TicketDetail) o;
		return Double.compare(this.getCheapestPrice(), temp.getCheapestPrice());
	}
}
