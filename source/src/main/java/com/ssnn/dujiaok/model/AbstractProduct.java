package com.ssnn.dujiaok.model;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
	
	private Date gmtExpire ;
	
	private String memo ;
	
	
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
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this , ToStringStyle.SHORT_PREFIX_STYLE) ;
	}
}
