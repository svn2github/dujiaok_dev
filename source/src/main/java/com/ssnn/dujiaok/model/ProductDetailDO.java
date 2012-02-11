package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ssnn.dujiaok.util.DateUtil;

public class ProductDetailDO implements Comparable<ProductDetailDO> {

	/**
	 * PK
	 */
	private int id;

	private String productId;
	/**
	 * 开始
	 */
	private Date gmtStart;

	/**
	 * 结束
	 */
	private Date gmtEnd;

	/**
	 * 双人价
	 */
	private BigDecimal doublePrice;

	/**
	 * 单人价
	 */
	private BigDecimal price;

	/**
	 * 儿童价
	 */
	private BigDecimal childPrice;
	private BigDecimal cheapestPrice;

	private Date gmtCreate;
	
	private List<Date> startDates = new ArrayList<Date>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Date getGmtStart() {
		return gmtStart;
	}

	public void setGmtStart(Date gmtStart) {
		this.gmtStart = (Date) gmtStart.clone();
		if (this.startDates.size() == 0) {
			Date temp = new Date();
			if (!this.gmtStart.before(temp)) {
				temp = this.gmtStart;
			}
			this.startDates.add(temp);
			return;
		}
		Date endDate = this.startDates.get(0);
		this.startDates.clear();
		DateUtil.fillDays(this.startDates, this.gmtStart, endDate);
	}

	public Date getGmtEnd() {
		return gmtEnd;
	}

	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = (Date) gmtEnd.clone();
		if (this.startDates.size() == 0) {
			this.startDates.add(this.gmtEnd);
			return;
		}
		Date startDate = this.startDates.get(0);
		this.startDates.clear();
		DateUtil.fillDays(this.startDates, startDate, this.gmtEnd);
	}

	public BigDecimal getDoublePrice() {
		return doublePrice;
	}
	
	public void setDoublePrice(BigDecimal doublePrice) {
		this.doublePrice = doublePrice;
		if (this.doublePrice == null) {
			return;
		}
		BigDecimal temp = doublePrice.divide(new BigDecimal("2"));
		if (this.price == null || temp.compareTo(this.price) < 0) {
			this.cheapestPrice = temp;
		} else {
			this.cheapestPrice = new BigDecimal(this.price.toString());
		}
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
		if (price == null) {
			return;
		}
		if (this.doublePrice == null) {
			this.cheapestPrice = new BigDecimal(this.price.toString());
			return;
		}
		BigDecimal temp = this.doublePrice.divide(new BigDecimal("2"));
		if (temp.compareTo(this.price) >= 0) {
			this.cheapestPrice = new BigDecimal(this.price.toString());
		} else {
			this.cheapestPrice = temp;
		}
	}

	public BigDecimal getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(BigDecimal childPrice) {
		this.childPrice = childPrice;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public List<Date> getStartDates() {
		return startDates;
	}

	public void setStartDates(List<Date> startDates) {
		this.startDates = startDates;
	}
	
	public BigDecimal getCheapestPrice() {
		return this.cheapestPrice != null ? this.cheapestPrice : new BigDecimal("-1");
	}
	
	@Override
	public int compareTo(ProductDetailDO o) {
		return this.getCheapestPrice().compareTo(o.getCheapestPrice());
	}
}
