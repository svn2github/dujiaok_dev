package com.ssnn.dujiaok.biz.dal;

import java.util.List;

import com.ssnn.dujiaok.model.TicketDetailDO;

/**
 * TicketDetail
 * @author shenjia.caosj 2012-1-10
 *
 */
public interface TicketDetailDAO {

	/**
	 * 
	 * @param ticketId
	 * @return
	 */
	List<TicketDetailDO> queryTicketDetail(int ticketId) ;
	
	/**
	 * 
	 * @param ticketDetail
	 */
	void insertTicketDetail(TicketDetailDO ticketDetail) ;
	
	/**
	 * 
	 * @param ticketId
	 */
	void deleteTicketDetails(int ticketId) ;
}
