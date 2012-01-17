package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 门票详细从表
 * @author shenjia.caosj 2012-1-10
 *
 */
public class TicketDetailDO extends AbstractProductDetail{

	
	/**
	 * 门票主表TICKET_ID
	 */
	private String ticketId ;
	/**
	 * 价格
	 */
	private BigDecimal price ;
	
	private Date gmtCreate ;
	
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	
	
}
