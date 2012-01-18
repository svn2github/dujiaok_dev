package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.model.TicketDO;

/**
 * TicketService
 * @author shenjia.caosj 2012-1-10
 *
 */
public interface TicketService {

	/**
	 * 获取Ticket,不包含detail
	 * @param ticketId
	 * @return
	 */
	TicketDO getTicket(String ticketId) ;
	
	/**
	 * 获取Ticket,包含detail
	 * @param ticketId
	 * @return
	 */
	TicketDO getTicketWithDetails(String ticketId) ;
	
	
	/**
	 * 创建Ticket,包含detail
	 * @param ticket
	 */
	TicketDO createTicketAndDetails(TicketDO ticket) ;
	
	
	/**
	 * 创建Ticket,包含Detail
	 * @param ticket
	 */
	TicketDO updateTicketAndDetails(TicketDO ticket) ;
	
}
