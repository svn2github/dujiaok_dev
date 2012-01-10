package com.ssnn.dujiaok.biz.dal;

import com.ssnn.dujiaok.model.TicketDO;

/**
 * 门票
 * @author shenjia.caosj 2012-1-9
 *
 */
public interface TicketDAO {
	
	/**
	 * 
	 * @param ticketId
	 * @return
	 */
	TicketDO queryTicket(int ticketId) ;

	/**
	 * 
	 * @param ticket
	 */
	void insertTicket(TicketDO ticket) ;
	
	
}
