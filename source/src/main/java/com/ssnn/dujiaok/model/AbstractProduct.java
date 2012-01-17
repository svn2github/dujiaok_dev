package com.ssnn.dujiaok.model;

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
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this , ToStringStyle.SHORT_PREFIX_STYLE) ;
	}
}
