package com.ssnn.dujiaok.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.dal.TicketDAO;
import com.ssnn.dujiaok.biz.dal.TicketDetailDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
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
			ticket.setDetails(ticketDetails) ;
		}
		return ticket ;
	}
	@Override
	public TicketDO createTicketAndDetails(TicketDO ticket) {
		List<TicketDetailDO> details = ticket.getDetails();
		if(details!=null){
			Date gmtExpire = getTicketExpireDate(details) ;
			ticket.setGmtExpire(gmtExpire) ;
			ticket.setProductId(UniqueIDUtil.getUniqueID(ProductEnums.TICKET)) ;
			ticketDAO.insertTicket(ticket) ;
			for(TicketDetailDO ticketDetail : details){
				ticketDetail.setTicketId(ticket.getProductId()) ;
				ticketDetailDAO.insertTicketDetail(ticketDetail) ;
			}
		}
		return ticket ;
	}

	@Override
	public TicketDO updateTicketAndDetails(TicketDO ticket) {
		List<TicketDetailDO> details = ticket.getDetails();
		
		if(details!=null){
			//删除之前的detail，重新插入
			ticketDetailDAO.deleteTicketDetails(ticket.getProductId()) ;
			Date gmtExpire = getTicketExpireDate(details) ;
			ticket.setGmtExpire(gmtExpire) ;
			ticketDAO.updateTicket(ticket) ;
			for(TicketDetailDO ticketDetail : details){
				ticketDetail.setTicketId(ticket.getProductId()) ;
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

	@Override
	public QueryResult<TicketDO> getTickets(Map<String, Object> condition,Pagination pagination) {
		pagination.setTotalCount(ticketDAO.countTickets(condition)) ;
		List<TicketDO> items = ticketDAO.queryTickets(condition, pagination) ;
		QueryResult<TicketDO> result = new QueryResult<TicketDO>(items,pagination) ;
		return result ;
		
	}
	
}
