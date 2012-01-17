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
	TicketDO getTicket(String ticketId) ;
	
	/**
	 * 获取Ticket,包含detail
	 * @param ticketId
	 * @return
	 */
	TicketDO getTicketWithDetails(String ticketId) ;
	
	/**
	 * 创建Ticket,不包含detail
	 * @param ticket
	 */
	TicketDO createTicket(TicketDO ticket) ;
	
	/**
	 * 创建Ticket,包含detail
	 * @param ticket
	 */
	TicketDO createTicketAndDetails(TicketDO ticket) ;
	
	/**
	 * 更新Ticket,不包含detail
	 * @param ticket
	 */
	TicketDO updateTicket(TicketDO ticket) ;
	
	/**
	 * 创建Ticket,包含Detail
	 * @param ticket
	 */
	TicketDO updateTicketAndDetails(TicketDO ticket) ;
	
	/**
	 * 删除detail
	 * @param ticketId
	 */
	void removeTicketDetails(String ticketId) ;
	
	/**
	 * 创建detail
	 * @param details
	 */
	void createTicketDetails(List<TicketDetailDO> details) ;
}
