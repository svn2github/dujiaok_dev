package com.ssnn.dujiaok.web.action.product;

import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class DetailAction extends BasicAction {

	private Object item;

	private String productId;
	
	private HotelRoomService hotelRoomService ;
	
	private TicketService ticketService ;
	
	private SelfDriveService selfDriveService ;
	
	@Override
	public String execute() throws Exception {
		
		if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTELROOM)){
			//房间
			item = hotelRoomService.getRoomWithDetails(productId) ;
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_TICKET)){
			//门票
			item = ticketService.getTicketWithDetails(productId);
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_SELFDRIVE)){
			//自驾
			item = selfDriveService.getSelfDriveWithDetails(productId) ;
		}
		
		if(item == null){
			return NONE ;
		}
				
		return SUCCESS ;
	}

	/** -------------------------------------------------------------- **/

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public String getProductId() {
		return productId;
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
