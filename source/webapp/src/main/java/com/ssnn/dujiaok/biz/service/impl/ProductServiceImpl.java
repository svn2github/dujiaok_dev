package com.ssnn.dujiaok.biz.service.impl;

import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.ProductService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.model.AbstractProduct;

public class ProductServiceImpl implements ProductService {

	private HotelRoomService hotelRoomService ;
	
	private TicketService ticketService;
	
	private SelfDriveService selfDriveService ;
	
	private HotelService hotelService ;
	
	@Override
	public AbstractProduct getProduct(String productId) {
		AbstractProduct product = null;
		if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTELROOM)){
			//房间
			product = hotelRoomService.getRoomWithDetails(productId);			
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_TICKET)){
			//门票
			product =  ticketService.getTicketWithDetails(productId);
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_SELFDRIVE)){
			//自驾
			product = selfDriveService.getSelfDriveWithDetails(productId);
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTEL)){
			//JIUDIAN
			product = hotelService.getHotel(productId);
		}
		
		return product ;
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

	public void setHotelRoomService(HotelRoomService hotelRoomService) {
		this.hotelRoomService = hotelRoomService;
	}

	
}
