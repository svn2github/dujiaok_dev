package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 门票详细从表
 * @author shenjia.caosj 2012-1-10
 *
 */
public class TicketDetailDO {

	/**
	 * pk
	 */
	private int id ;
	
	/**
	 * 门票主表ID
	 */
	private int ticketId ;
	/**
	 * 价格
	 */
	private BigDecimal price ;
	/**
	 * 开始
	 */
	private Date gmtStart ;
	
	/**
	 * 结束
	 */
	private Date gmtEnd ;
	
	private Date gmtCreate ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getGmtStart() {
		return gmtStart;
	}

	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}

	public Date getGmtEnd() {
		return gmtEnd;
	}

	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	
}
