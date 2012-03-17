package com.ssnn.dujiaok.web.action.product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class DetailAction extends BasicAction {

	private Object product;

	private String productId;
	
	private HotelRoomService hotelRoomService ;
	
	private TicketService ticketService;
	
	private SelfDriveService selfDriveService ;
	
	private HotelService hotelService ;

	private static final Log  LOGGER = LogFactory.getLog(DetailAction.class);
	@Override
	public String execute() throws Exception {
		
		if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTELROOM)){
			//房间
			product = hotelRoomService.getRoomWithDetails(productId);
			return ProductConstant.ROOM ;
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_TICKET)){
			//门票
			product =  ticketService.getTicketWithDetails(productId);
			if(product != null){
				return ProductConstant.TICKET ;
			}
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_SELFDRIVE)){
			//自驾
			product = selfDriveService.getSelfDriveWithDetails(productId);
			if(product != null){
				return ProductConstant.SELF_DRIVE ;
			}
			return ProductConstant.SELF_DRIVE ;
		}
//		else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTEL)){
//			if(product != null){
//				product = hotelService.getHotel(productId);
//			}
//			return ProductConstant.HOTEL ;
//		}
				
		return ProductConstant.NOT_EXIST;
	}


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

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}	
	
}