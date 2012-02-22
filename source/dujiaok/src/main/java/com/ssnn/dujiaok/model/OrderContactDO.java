package com.ssnn.dujiaok.model;

import java.util.Date;

/**
 * 订单联系人信息
 * @author shenjia.caosj 2012-2-4
 *
 */
public class OrderContactDO {

	private int id ;
	
	private String orderId ;
	
	private String name ;
	
	private String mobile ;
	
	private String email ;
	
	/**
	 * 是否主联系人
	 */
	private String isMain ;
	
	/**
	 * 证件类型
	 */
	private String certificateType ;
	
	/**
	 * 证件号码
	 */
	private String certificateNumber ;
	
	private Date gmtCreate ;

	
	
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
	
	
}
