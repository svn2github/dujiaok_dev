package com.ssnn.dujiaok.web.action.ajax;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class QueryHotelAction extends BasicAction {

	private String hotelId;

	private HotelDO hotel;
	
	private HotelService hotelService ;

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	public HotelDO getHotel() {
		return hotel;
	}

	@Override
	public String execute() throws Exception {
		if(StringUtils.isNotBlank(hotelId)){
			hotel = hotelService.getHotel(hotelId) ;
		}
		return SUCCESS ;
	}
}
