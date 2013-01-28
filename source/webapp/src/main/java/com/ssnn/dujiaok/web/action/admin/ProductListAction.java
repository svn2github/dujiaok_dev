package com.ssnn.dujiaok.web.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.model.AbstractProduct;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * action
 * @author shenjia.caosj 2012-1-20
 *
 */
@SuppressWarnings("serial")
public class ProductListAction extends BasicAction implements ModelDriven<Pagination>{

	private String name ;
	
	private String destProvince ;
	
	private String destCity ;
	
	private Date gmtExpire ;
	
	private String productId ;
	
	private Pagination pagination =  new Pagination(1);
	
	private String type ;
	
	@SuppressWarnings("rawtypes")
	private QueryResult result ;
	
	private Map<String,AbstractProduct> hotelMap ;
	
	private TicketService ticketService ;
	
	private SelfDriveService selfDriveService ;
	
	private HotelService hotelService ;
	
	private HotelRoomService hotelRoomService ;
	
	public String selfDrive() throws Exception {
		type = "selfDrive" ;
		return SUCCESS ;
	}
	
	public String ticket() throws Exception {
		type = "ticket" ;
		return SUCCESS ;
	}
	
	public String hotel() throws Exception {
		Map<String,Object> condition = new HashMap<String,Object>() ;
		if(StringUtils.isNotBlank(name)){
			condition.put("name", name) ;
		}
		if(StringUtils.isNotBlank(destProvince)){
			condition.put("destProvince", destProvince) ;
		}
		if(StringUtils.isNotBlank(destCity)){
			condition.put("destCity", destCity) ;
		}
		if(StringUtils.isNotBlank(productId)){
			condition.put("productId", productId) ;
		}
		if(gmtExpire != null){
			condition.put("gmtExpire", gmtExpire) ;
		}
		
		QueryResult<HotelDO> list = hotelService.getHotels(condition, pagination) ;
		if(!CollectionUtils.isEmpty(list.getItems())){
			for(HotelDO hotel : list.getItems()){
				hotel.setRooms(hotelRoomService.getRooms(hotel.getProductId())) ;
			}
		}
		result = list ;
		return SUCCESS ;
	}
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> condition = new HashMap<String,Object>() ;
		if(StringUtils.isNotBlank(name)){
			condition.put("name", name) ;
		}
		if(StringUtils.isNotBlank(destProvince)){
			condition.put("destProvince", destProvince) ;
		}
		if(StringUtils.isNotBlank(destCity)){
			condition.put("destCity", destCity) ;
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
		}else if(StringUtils.equals(type, "hotelRoom")) {
			result = hotelRoomService.getRooms(condition, pagination) ;
			if(CollectionUtils.isNotEmpty(result.getItems())){
				List<HotelRoomDO> hrList = result.getItems() ;
				List<String> hotelIDList = getHotelIdList(hrList) ;
				List hotelList = hotelService.getHotelsByProductIds(hotelIDList) ;
				hotelMap = ProductUtils.convert2Map(hotelList) ;
			}
		}
		return SUCCESS ;
	}
	
	private List<String> getHotelIdList(List<HotelRoomDO> hrList){
		List<String> hotelIDList = new ArrayList<String>() ;
		if(CollectionUtils.isNotEmpty(hrList)){
			for(HotelRoomDO hr : hrList){
				String hotelId = hr.getHotelId() ;
				if(StringUtils.isNotBlank(hotelId)){
					hotelIDList.add(hotelId) ;
				}
			}
		}
		return hotelIDList ;
	}
	
	
	
	@Override
	public Pagination getModel() {
		return pagination ;
	}
	
	/**-------------------------------------------------------------------[**/
	
	
	
	
	public String getName() {
		return name;
	}


	public void setHotelRoomService(HotelRoomService hotelRoomService) {
		this.hotelRoomService = hotelRoomService;
	}

	@SuppressWarnings("rawtypes")
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


	public String getDestProvince() {
		return destProvince;
	}

	public void setDestProvince(String destProvince) {
		this.destProvince = destProvince;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
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

	public Map<String, AbstractProduct> getHotelMap() {
		return hotelMap;
	}

	


	
}
