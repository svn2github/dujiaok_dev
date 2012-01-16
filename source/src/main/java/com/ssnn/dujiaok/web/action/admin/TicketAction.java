package com.ssnn.dujiaok.web.action.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mchange.v1.util.ArrayUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.TicketDetailDO;
import com.ssnn.dujiaok.util.StringListConventUtil;
import com.ssnn.dujiaok.web.action.BasicAction;

public class TicketAction extends BasicAction implements ModelDriven<TicketDO>{

	private TicketDO ticket = new TicketDO() ;
	
	private TicketService ticketService;
	
	private List<String> payTypesList ;
	private List<String> imagesList ;
	private List<String> productTypesList ;
	
	public List<String> getPayTypesList() {
		return payTypesList;
	}

	public void setPayTypesList(List<String> payTypesList) {
		this.payTypesList = payTypesList;
	}

	public List<String> getImagesList() {
		return imagesList;
	}

	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}

	public List<String> getProductTypesList() {
		return productTypesList;
	}

	public void setProductTypesList(List<String> productTypesList) {
		this.productTypesList = productTypesList;
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
		if(ticket!=null && ticket.getId()>0){
			ticket = ticketService.getTicketWithDetails(ticket.getId()) ;
			if(ticket != null){
				payTypesList = StringListConventUtil.toList(ticket.getPayTypes()) ;
				productTypesList = StringListConventUtil.toList(ticket.getProductTypes()) ;
				imagesList = StringListConventUtil.toList(ticket.getImages()) ;
				
			}
		}
		return SUCCESS ;
	}
	
	/**
	 * 创建门票
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		
		ticket.setImages(StringListConventUtil.toString(imagesList)) ;
		ticket.setPayTypes(StringListConventUtil.toString(payTypesList)) ;
		ticket.setProductTypes(StringListConventUtil.toString(productTypesList)) ;
		//ticketService.createTicketAndDetails(ticket) ;
		
		return SUCCESS ;
	}
	
	@Override
	public TicketDO getModel() {
		return ticket ;
	}

}
