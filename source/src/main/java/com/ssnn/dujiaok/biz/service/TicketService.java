package com.ssnn.dujiaok.biz.service;

import java.util.List;

import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.TicketDetailDO;

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
	TicketDO getTicket(int ticketId) ;
	
	/**
	 * 获取Ticket,包含detail
	 * @param ticketId
	 * @return
	 */
	TicketDO getTicketWithDetails(int ticketId) ;
	
	/**
	 * 创建Ticket,不包含detail
	 * @param ticket
	 */
	void createTicket(TicketDO ticket) ;
	
	/**
	 * 创建Ticket,包含detail
	 * @param ticket
	 */
	void createTicketAndDetails(TicketDO ticket) ;
	
	/**
	 * 删除detail
	 * @param ticketId
	 */
	void removeTicketDetails(int ticketId) ;
	
	/**
	 * 创建detail
	 * @param details
	 */
	void createTicketDetails(List<TicketDetailDO> details) ;
}
