package com.ssnn.dujiaok.web.action.admin;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.util.StringListConventUtil;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class TicketAction extends BasicAction implements ModelDriven<TicketDO>{

	private TicketDO ticket = new TicketDO() ;
	
	private TicketService ticketService;
	
	private List<String> payTypesList ;
	private List<String> imagesList ;
	private List<String> productTypesList ;
	
	@Override
	public String execute() throws Exception {
		if(ticket!=null && StringUtils.isNotBlank(ticket.getProductId())){
			ticket = ticketService.getTicketWithDetails(ticket.getProductId()) ;
			if(ticket != null){
				payTypesList = StringListConventUtil.toList(ticket.getPayTypes()) ;
				productTypesList = StringListConventUtil.toList(ticket.getProductTypes()) ;
				imagesList = StringListConventUtil.toList(ticket.getImages()) ;
				
			}
		}
		return SUCCESS ;
	}
	
	/**
	 * 发布门票
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		
		ticket.setImages(StringListConventUtil.toString(imagesList)) ;
		ticket.setPayTypes(StringListConventUtil.toString(payTypesList)) ;
		ticket.setProductTypes(StringListConventUtil.toString(productTypesList)) ;
		if(StringUtils.isBlank(ticket.getProductId())){
			ticket = ticketService.createTicketAndDetails(ticket) ;
		}else{
			ticket = ticketService.updateTicketAndDetails(ticket) ;
		}
		
		return SUCCESS ;
	}
	
	/**
	 * 发布成功
	 * @return
	 * @throws Exception
	 */
	public String success() throws Exception {
		return SUCCESS ;
	}
	
	@Override
	public TicketDO getModel() {
		return ticket ;
	}

	/**-----------------------------------------------------------------**/
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
}
