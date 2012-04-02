package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateUtils;

import com.ssnn.dujiaok.util.ArrayStringUtils;
import com.ssnn.dujiaok.util.DateUtil;
import com.ssnn.dujiaok.util.ProductUtils;

/**
 * 
 * @author shenjia.caosj 2012-1-17
 * 
 */
public abstract class AbstractProduct {

	/**
	 * PK
	 */
	private int id;

	/**
	 * 规则生成ID
	 */
	private String productId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 目的地(详细地址)
	 */
	private String destAddr;

	/**
	 * 目的地(省)
	 */
	private String destProvince;
	
	/**
	 * 目的地(市)
	 */
	private String destCity;
	/**
	 * 目的地(地区)
	 */
	private String destArea;
	/**
	 * 定位坐标
	 */
	private String locationCode;
	/**
	 * 产品描述图片.
	 */
	private String images;

	
	private Date gmtExpire;

	private String memo;
	/**
	 * 
	 */
	private List<ProductDetailDO> details;

	/**
	 * 支付方式
	 */
	private String payTypes;

	/**
	 * 市场价
	 */
	private BigDecimal marketPrice;
	
	/**
	 * 最低价，优惠价
	 */
	private BigDecimal bottomPrice ;

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

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
	}

	public String getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(String payTypes) {
		this.payTypes = payTypes;
	}

	public BigDecimal getBottomPrice() {
		return bottomPrice;
	}

	public void setBottomPrice(BigDecimal bottomPrice) {
		this.bottomPrice = bottomPrice;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	// ////////////////////////////////
	
	public List<String> getImageList(){
		return ArrayStringUtils.toList(images) ;
	}
	
	public List<String> getPayTypeList(){
		return ArrayStringUtils.toList(payTypes) ;
	}
	
	
	public List<DetailItemDO> getDefaultDetailItems(){
		Date start = new Date() ;
		com.ssnn.dujiaok.util.DateUtils.setTime(start, 0, 0, 0) ;
		start = DateUtils.addDays(start, 1) ;//预定一天后
		Date end = new Date() ;
		end = org.apache.commons.lang.time.DateUtils.addMonths(end, 1) ;
		return getDetailItems(start, end) ;
	}
	
	/**
	 * 
	 * @param start 
	 * @param end
	 * @return
	 */
	public List<DetailItemDO> getDetailItems(Date start , Date end){
		return ProductUtils.getDetailItems(details, start, end) ;
	}

}