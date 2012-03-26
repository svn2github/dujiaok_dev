package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ssnn.dujiaok.util.ProductUtils;

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
	
	private Date gmtCreate;

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
		this.gmtStart =  gmtStart ;
	}

	public Date getGmtEnd() {
		return gmtEnd;
	}

	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd ;
	}

	public BigDecimal getDoublePrice() {
		return doublePrice;
	}
	
	public void setDoublePrice(BigDecimal doublePrice) {
		this.doublePrice = doublePrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	@Override
	public int compareTo(ProductDetailDO o) {
		return this.getBottomPrice().compareTo(o.getBottomPrice());
	}
	
	/**
	 * extra functions 
	 */
	
	public BigDecimal getBottomPrice() {
		return ProductUtils.calcBottomPrice(this) ;
	}
	
}
