package com.ssnn.dujiaok.biz.service.impl;

import java.util.List;

import com.ssnn.dujiaok.biz.dal.TicketDAO;
import com.ssnn.dujiaok.biz.dal.TicketDetailDAO;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.TicketDetailDO;

public class TicketServiceImpl implements TicketService{

	private TicketDAO ticketDAO ;
	private TicketDetailDAO ticketDetailDAO ;
	
	
	public void setTicketDAO(TicketDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}

	public void setTicketDetailDAO(TicketDetailDAO ticketDetailDAO) {
		this.ticketDetailDAO = ticketDetailDAO;
	}

	@Override
	public TicketDO getTicket(int ticketId) {
		return ticketDAO.queryTicket(ticketId) ;
	}

	@Override
	public TicketDO getTicketWithDetails(int ticketId) {
		TicketDO ticket = ticketDAO.queryTicket(ticketId) ;
		if(ticket != null){
			List<TicketDetailDO> ticketDetails = ticketDetailDAO.queryTicketDetail(ticketId) ;
			ticket.setTicketDetails(ticketDetails) ;
		}
		return ticket ;
	}
	
	@Override
	public void createTicket(TicketDO ticket) {
		ticketDAO.insertTicket(ticket) ;
	}

	@Override
	public void createTicketAndDetails(TicketDO ticket) {
		ticketDAO.insertTicket(ticket) ;
		List<TicketDetailDO> details = ticket.getTicketDetails();
		if(details!=null){
			for(TicketDetailDO ticketDetail : details){
				ticketDetailDAO.insertTicketDetail(ticketDetail) ;
			}
		}
	}

	@Override
	public void removeTicketDetails(int ticketId) {
		ticketDetailDAO.deleteTicketDetails(ticketId) ;
	}

	@Override
	public void createTicketDetails(List<TicketDetailDO> details) {
		for(TicketDetailDO ticketDetail : details){
			ticketDetailDAO.insertTicketDetail(ticketDetail) ;
		}
	}

	
	
	
}
