package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ssnn.dujiaok.util.StringListConventUtil;

/**
 * 
 * @author shenjia.caosj 2012-1-17
 *
 */
public abstract class AbstractProduct {

	/**
	 * PK
	 */
	private int id ;
	
	/**
	 * 规则生成ID
	 */
	private String productId ;
	
	/**
	 * 名称
	 */
	private String name ;
	
	/**
	 * 目的地(详细地址)
	 */
	private String destAddr ;
	
	/**
	 * 目的地(省)
	 */
	private String destProvince ;
	/**
	 * 目的地(市)
	 */
	private String destCity ;
	/**
	 * 目的地(地区)
	 */
	private String destArea ;
	/**
	 * 定位坐标
	 */
	private String locationCode ;
	/**
	 * 产品描述图片.
	 */
	private String images;
	
	private List<String> pictureUrls = new ArrayList<String>();
	
	private Date gmtExpire ;
	
	private String memo;
	/**
	 * 
	 */
	private List<ProductDetailDO> details;
	
	private String payTypes;

	protected BigDecimal cheapestPrice;
	
	public Date getGmtExpire() {
		return gmtExpire;
	}
	public void setGmtExpire(Date gmtExpire) {
		this.gmtExpire = gmtExpire;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDestAddr() {
		return destAddr;
	}
	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
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
	public String getDestArea() {
		return destArea;
	}
	public void setDestArea(String destArea) {
		this.destArea = destArea;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
		
		this.pictureUrls = StringListConventUtil.toList(this.images) ;
	}
	
	public List<String> getPictureUrls() {
		return this.pictureUrls;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<ProductDetailDO> getDetails() {
		return details;
	}

	public void setDetails(List<ProductDetailDO> details) {
		this.details = details;
		if (this.details == null) {
			this.cheapestPrice = new BigDecimal("-1");
		}
		this.cheapestPrice = Collections.min(details).getCheapestPrice();
	}
	
	public void setCheapestPrice(BigDecimal cheapestPrice) {
		this.cheapestPrice = cheapestPrice;
	}

	public String getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(String payTypes) {
		this.payTypes = payTypes;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this , ToStringStyle.SHORT_PREFIX_STYLE) ;
	}
}