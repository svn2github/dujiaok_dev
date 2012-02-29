package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ssnn.dujiaok.util.StringListConventUtil;

/**
 * 搜索
 * @author shenjia.caosj 2012-2-28
 *
 */
public class SearchDO {

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this , ToStringStyle.SHORT_PREFIX_STYLE) ;
	}
	
	private String productId ;
	
	private String name ;
	
	private BigDecimal price ;
	
	private String destArea ;
	
	private String destProvince ;
	
	private String destCity ;
	
	private String destAddr ;
	
	private int days ;
	
	private BigDecimal marketPrice ;
	
	private String intro ;
	
	private String images ;
	
	public String getFirstImage(){
		if(StringUtils.isBlank(images)){
			return images;
		}
		List<String> imagesArray = StringListConventUtil.toList(images) ;
		return imagesArray.iterator().next() ;
	}
	
	/**
	 * ---------------------------------------------------------------------------------
	 */

	public String getDestAddr() {
		return destAddr;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
	public String getDestArea() {
		return destArea;
	}

	public void setDestArea(String destArea) {
		this.destArea = destArea;
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

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	
}
