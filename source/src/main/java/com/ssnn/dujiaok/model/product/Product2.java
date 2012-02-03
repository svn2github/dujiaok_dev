package com.ssnn.dujiaok.model.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ssnn.dujiaok.util.string.StringUtil;

public class Product2 {
	/**
	 * 编号.
	 */
	private String id;
	/**
	 * 名称.
	 */
	private String name;
	/**
	 * 附加产品.
	 */
	private String attachProduct;
	/**
	 * 市场价.
	 */
	private Double marketPrice;
	/**
	 * 支付方式.
	 */
	private String payments;
	/**
	 * 产品类别.
	 */
	private String series;
	/**
	 * 产品图片.
	 */
	private List<String> pictureUrls;
	/**
	 * 产品简介.
	 */
	private String desc;
	/**
	 * 费用说明.
	 */
	private String feeDesc;
	/**
	 * 备注.
	 */
	private String remark;
	/**
	 * 最低价.
	 */
	private Double cheapestPrice;
	/**
	 * 产品类别.1:自驾;2:房间;3:酒店;4:门票.
	 */
	private Integer type;
	/**
	 * .
	 */
	private List<? extends ProductDetail> details;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public List<? extends ProductDetail> getDetails() {
		return details;
	}
	public void setDetails(List<? extends ProductDetail> details) {
		this.details = details;
		if (details.size() == 0) {
			this.cheapestPrice = -1.0;
		} else {
			this.cheapestPrice = ((ProductDetail) Collections.min(details)).getCheapestPrice();
		}
	}
	public String getAttachProduct() {
		return attachProduct;
	}
	public void setAttachProduct(String attachProduct) {
		this.attachProduct = attachProduct;
	}
	public String getPayments() {
		return payments;
	}
	public void setPayments(String payments) {
		this.payments = payments;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getFeeDesc() {
		return feeDesc;
	}
	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getCheapestPrice() {
		return cheapestPrice;
	}
}
