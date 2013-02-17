package com.ssnn.dujiaok.biz.dal;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.TicketDO;

/**
 * 门票
 * @author langben 2012-1-9
 *
 */
public interface TicketDAO {
	
	/**
	 * 
	 * @param ticketId
	 * @return
	 */
	TicketDO queryTicket(String ticketId) ;
	
	/**
	 * 
	 * @param ticket
	 */
	void insertTicket(TicketDO ticket) ;
	
	/**
	 * 
	 * @param ticket
	 */
	void updateTicket(TicketDO ticket) ;
	
	/**
	 * 查询Ticket
	 * @param condition 条件  key=字段名，value=字段值
	 * @param pagination 分页BEAN
	 * @return
	 */
	List<TicketDO> queryTickets(Map<String,Object> condition , Pagination pagination) ;
	
	/**
	 * 
	 * @param condition
	 * @return
	 */
	int countTickets(Map<String,Object> condition) ;
	/**
	 * 
	 * @param ticketId
	 */
	void deleteTicket(String ticketId) ;
}
