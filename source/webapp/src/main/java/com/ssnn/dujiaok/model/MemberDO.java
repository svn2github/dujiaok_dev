package com.ssnn.dujiaok.model;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author langben 2011-12-27
 *
 */
public class MemberDO {

	private int id ;
	
	/**
	 * 登陆id
	 */
	private String memberId ;
	
	/**
	 * password
	 */
	private String password ;
	
	/**
	 * 昵称
	 */
	private String nickname ;
	
	/**
	 * 手机
	 */
	private String mobileNo ;
	
	/**
	 * email
	 */
	private String email ;
	
	/**
	 * 退款账号
	 */
	private String refundAccount ;
	
	/**
	 * 退款账号类型
	 */
	private String refundAccountType ;
	
	/**
	 * 退款账号类型银行
	 */
	private String refundAccountTypeBank ;
	
	/**
	 * 退款账号姓名
	 */
	private String refundAccountName ;
	
	/**
	 * 开户行省
	 */
	private String bankProvince ;
	
	/**
	 * 开户行省
	 */
	private String bankCity ;
	
	/**
	 * 开户行省
	 */
	private String bankArea ;
	
	/**
	 * 开户分行
	 */
	private String bankBranchbank ;
	
	/**
	 * 邮寄 省
	 */
	private String mailingProvince ;
	
	/**
	 * 邮寄 市
	 */
	private String mailingCity ;
	
	/**
	 * 邮寄 区域
	 */
	private String mailingArea ;
	
	/**
	 * 邮寄 详细地址
	 */
	private String mailingAddr ;
	
	/**
	 * 姓名
	 */
	private String mailingName ;
	
	/**
	 * 邮编
	 */
	private String mailingZipcode ;

	private Date gmtCreate ;
	
	private Date gmtModified ;
	
	
	public String getRefundAccountTypeBank() {
		return refundAccountTypeBank;
	}

	public void setRefundAccountTypeBank(String refundAccountTypeBank) {
		this.refundAccountTypeBank = refundAccountTypeBank;
	}

	public String getRefundAccount() {
		return refundAccount;
	}

	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}

	public String getMailingName() {
		return mailingName;
	}

	public void setMailingName(String mailingName) {
		this.mailingName = mailingName;
	}

	public String getMailingZipcode() {
		return mailingZipcode;
	}

	public void setMailingZipcode(String mailingZipcode) {
		this.mailingZipcode = mailingZipcode;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankArea() {
		return bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}

	public String getBankBranchbank() {
		return bankBranchbank;
	}

	public void setBankBranchbank(String bankBranchbank) {
		this.bankBranchbank = bankBranchbank;
	}

	public String getRefundAccountType() {
		return refundAccountType;
	}

	public void setRefundAccountType(String refundAccountType) {
		this.refundAccountType = refundAccountType;
	}

	public String getRefundAccountName() {
		return refundAccountName;
	}

	public void setRefundAccountName(String refundAccountName) {
		this.refundAccountName = refundAccountName;
	}

	public String getMailingProvince() {
		return mailingProvince;
	}

	public void setMailingProvince(String mailingProvince) {
		this.mailingProvince = mailingProvince;
	}

	public String getMailingCity() {
		return mailingCity;
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

	public String getMailingArea() {
		return mailingArea;
	}

	public void setMailingArea(String mailingArea) {
		this.mailingArea = mailingArea;
	}

	public String getMailingAddr() {
		return mailingAddr;
	}

	public void setMailingAddr(String mailingAddr) {
		this.mailingAddr = mailingAddr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE) ;
	}
	
}
