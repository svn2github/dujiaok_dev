package com.ssnn.dujiaok.biz.service.impl;

import java.util.Date;
import java.util.List;

import com.ssnn.dujiaok.biz.dal.TicketDAO;
import com.ssnn.dujiaok.biz.dal.TicketDetailDAO;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.TicketDetailDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

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
	public TicketDO getTicket(String ticketId) {
		return ticketDAO.queryTicket(ticketId) ;
	}

	@Override
	public TicketDO getTicketWithDetails(String ticketId) {
		TicketDO ticket = ticketDAO.queryTicket(ticketId) ;
		if(ticket != null){
			List<TicketDetailDO> ticketDetails = ticketDetailDAO.queryTicketDetail(ticketId) ;
			ticket.setTicketDetails(ticketDetails) ;
		}
		return ticket ;
	}
	
	@Override
	public TicketDO createTicket(TicketDO ticket) {
		ticket.setTicketId(UniqueIDUtil.getUniqueID(ProductEnums.TICKET)) ;
		ticketDAO.insertTicket(ticket) ;
		return ticket ;
	}

	@Override
	public TicketDO createTicketAndDetails(TicketDO ticket) {
		List<TicketDetailDO> details = ticket.getTicketDetails();
		if(details!=null){
			Date gmtExpire = getTicketExpireDate(details) ;
			ticket.setGmtExpire(gmtExpire) ;
			ticket.setTicketId(UniqueIDUtil.getUniqueID(ProductEnums.TICKET)) ;
			ticketDAO.insertTicket(ticket) ;
			for(TicketDetailDO ticketDetail : details){
				ticketDetail.setTicketId(ticket.getTicketId()) ;
				ticketDetailDAO.insertTicketDetail(ticketDetail) ;
			}
		}
		return ticket ;
	}

	@Override
	public void removeTicketDetails(String ticketId) {
		ticketDetailDAO.deleteTicketDetails(ticketId) ;
	}

	@Override
	public void createTicketDetails(List<TicketDetailDO> details) {
		for(TicketDetailDO ticketDetail : details){
			ticketDetailDAO.insertTicketDetail(ticketDetail) ;
		}
	}

	@Override
	public TicketDO updateTicket(TicketDO ticket) {
		ticketDAO.updateTicket(ticket) ;
		return ticket ;
	}

	@Override
	public TicketDO updateTicketAndDetails(TicketDO ticket) {
		List<TicketDetailDO> details = ticket.getTicketDetails();
		
		if(details!=null){
			ticketDetailDAO.deleteTicketDetails(ticket.getTicketId()) ;
			Date gmtExpire = getTicketExpireDate(details) ;
			ticket.setGmtExpire(gmtExpire) ;
			ticketDAO.updateTicket(ticket) ;
			for(TicketDetailDO ticketDetail : details){
				ticketDetail.setTicketId(ticket.getTicketId()) ;
				ticketDetailDAO.insertTicketDetail(ticketDetail) ;
			}
		}
		return ticket ;
	}

	
	private Date getTicketExpireDate(List<TicketDetailDO> details){
		Date gmtExpire = null ;
		for(TicketDetailDO detail : details){
			Date gmtEnd = detail.getGmtEnd() ;
			if(gmtExpire == null){
				gmtExpire = gmtEnd ;
			}else{
				if(gmtExpire.before(gmtEnd)){
					gmtExpire = gmtEnd ;
				}
			}
		}
		return gmtExpire ;
	}
	
}
