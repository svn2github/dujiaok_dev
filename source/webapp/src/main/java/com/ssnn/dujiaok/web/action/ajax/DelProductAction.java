package com.ssnn.dujiaok.web.action.ajax;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 删除产品
 * @author langben 2012-2-3
 *
 */
@SuppressWarnings("serial")
public class DelProductAction  extends BasicAction{
	
	private static final Log logger = LogFactory.getLog(DelProductAction.class) ;

	private HotelService hotelService ;
	
	private HotelRoomService hotelRoomService ;
	
	private TicketService ticketService ;
	
	private SelfDriveService selfDriveService ;

	private String result ;
	
	private String productId ;
	
	@Override
	public String execute() throws Exception {
		result = ERROR ;
		
		try{
			if(StringUtils.startsWithIgnoreCase(productId , Constant.PREFIX_TICKET)){
				ticketService.deleteTicket(productId) ;
				result = SUCCESS ;
			}else if(StringUtils.startsWithIgnoreCase(productId,Constant.PREFIX_HOTEL)){
				hotelService.deleteHotel(productId) ;
				result = SUCCESS ;
			}else if(StringUtils.startsWithIgnoreCase(productId,Constant.PREFIX_HOTELROOM)){
				hotelRoomService.deleteHotelRoom(productId) ;
				result = SUCCESS ;
			}else if(StringUtils.startsWithIgnoreCase(productId,Constant.PREFIX_SELFDRIVE)){
				selfDriveService.deleteSelfDrive(productId) ;
				result = SUCCESS ;
			}
		}catch(Exception e){
			logger.error("delete product error [" + productId + "] | " + e.getMessage() , e) ;
		}
	
		return SUCCESS ;
	}
	
		
	public String getResult() {
		return result;
	}
	
	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	/**---------------------------------------------------------------**/
	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
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
