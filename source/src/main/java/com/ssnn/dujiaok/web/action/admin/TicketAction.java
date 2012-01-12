package com.ssnn.dujiaok.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.TicketDetailDO;
import com.ssnn.dujiaok.web.action.BasicAction;

public class TicketAction extends BasicAction implements ModelDriven<TicketDO>{

	private TicketDO ticket = new TicketDO() ;
	
	private List<TicketDetailDO> ticketDetails = new ArrayList<TicketDetailDO>() ;
	
	private List<String> payTypes = new ArrayList<String>() ;
	private List<String> images = new ArrayList<String>() ;
	private List<String> productTypes = new ArrayList<String>() ;
	
	private TicketService ticketService;
	
	public List<TicketDetailDO> getTicketDetails() {
		return ticketDetails;
	}

	public void setTicketDetails(List<TicketDetailDO> ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public TicketDO getTicket() {
		return ticket;
	}

	public void setTicket(TicketDO ticket) {
		this.ticket = ticket;
	}

	@Override
	public String execute() throws Exception {
		//ticket = ticketService.getTicketWithDetails(ticket.getId()) ;
		return SUCCESS ;
	}
	
	/**
	 * 创建门票
	 * @return
	 * @throws Exception
	 */
	public String docreate() throws Exception {
		if(ticketDetails.isEmpty()){
			addActionError("门票信息不完整") ;
			return INPUT ;
		}
		ticket.setTicketDetails(ticketDetails) ;
		ticketService.createTicketAndDetails(ticket) ;
		return SUCCESS ;
	}
	
	@Override
	public TicketDO getModel() {
		return ticket ;
	}

}
