package com.ssnn.dujiaok.web.action.product;

import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class DetailAction extends BasicAction {

	private Object product;

	private String productId;
	
	private HotelRoomService hotelRoomService ;
	
	private TicketService ticketService ;
	
	private SelfDriveService selfDriveService ;
	
	@Override
	public String execute() throws Exception {
		
		if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTELROOM)){
			//房间
			product = hotelRoomService.getRoomWithDetails(productId) ;
			return "room" ;
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_TICKET)){
			//门票
			product = ticketService.getTicketWithDetails(productId);
			return "ticket" ;
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_SELFDRIVE)){
			//自驾
			product = selfDriveService.getSelfDriveWithDetails(productId) ;
			return "selfDrive" ;
		}
		
		if(product == null){
			return NONE ;
		}
				
		return SUCCESS ;
	}

	/** -------------------------------------------------------------- **/

	

	public String getProductId() {
		return productId;
	}

	public Object getProduct() {
		return product;
	}

	public void setProduct(Object product) {
		this.product = product;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setHotelRoomService(HotelRoomService hotelRoomService) {
		this.hotelRoomService = hotelRoomService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public void setSelfDriveService(SelfDriveService selfDriveService) {
		this.selfDriveService = selfDriveService;
	}

	
}
