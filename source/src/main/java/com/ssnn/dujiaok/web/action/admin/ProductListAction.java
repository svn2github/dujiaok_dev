package com.ssnn.dujiaok.web.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * action
 * @author shenjia.caosj 2012-1-20
 *
 */
@SuppressWarnings("serial")
public class ProductListAction extends BasicAction implements ModelDriven<Pagination>{

	private String name ;
	
	private String province ;
	
	private String city ;
	
	private Date gmtExpire ;
	
	private String productId ;
	
	private Pagination pagination =  new Pagination(1);
	
	private String type ;
	
	@SuppressWarnings("rawtypes")
	private QueryResult result ;
	
	private TicketService ticketService ;
	
	private SelfDriveService selfDriveService ;
	
	private HotelService hotelService ;
	
	public String selfDrive() throws Exception {
		type = "selfDrive" ;
		return SUCCESS ;
	}
	
	public String ticket() throws Exception {
		type = "ticket" ;
		return SUCCESS ;
	}
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> condition = new HashMap<String,Object>() ;
		if(StringUtils.isNotBlank(name)){
			condition.put("name", name) ;
		}
		if(StringUtils.isNotBlank(province)){
			condition.put("destProvince", province) ;
		}
		if(StringUtils.isNotBlank(city)){
			condition.put("destCity", city) ;
		}
		if(StringUtils.isNotBlank(productId)){
			condition.put("productId", productId) ;
		}
		if(gmtExpire != null){
			condition.put("gmtExpire", gmtExpire) ;
		}
		
		if(StringUtils.equals(type,"ticket")){
			result = ticketService.getTickets(condition, pagination) ;
		}else if(StringUtils.equals(type, "hotel")){
			result = hotelService.getHotels(condition, pagination) ;
		}else if(StringUtils.equals(type, "selfDrive")){
			result = selfDriveService.getSelfDrives(condition, pagination) ;
		}
		return SUCCESS ;
	}
	
	
	
	@Override
	public Pagination getModel() {
		return pagination ;
	}
	
	/**-------------------------------------------------------------------[**/
	
	
	
	
	public String getName() {
		return name;
	}


	public QueryResult getResult() {
		return result;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public void setSelfDriveService(SelfDriveService selfDriveService) {
		this.selfDriveService = selfDriveService;
	}

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public Date getGmtExpire() {
		return gmtExpire;
	}


	public void setGmtExpire(Date gmtExpire) {
		this.gmtExpire = gmtExpire;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public Pagination getPagination() {
		return pagination;
	}


	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	


	
}
