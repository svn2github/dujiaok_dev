package com.ssnn.dujiaok.web.action.admin;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author shenjia.caosj 2012-1-17
 *
 */
public class HotelAction extends BasicAction implements ModelDriven<HotelDO>{

	private HotelDO hotel = new HotelDO() ;
	
	private HotelService hotelService ;
	
	@Override
	public String execute() throws Exception {
		if(hotel!=null && StringUtils.isNotBlank(hotel.getHotelId())){
			hotel = hotelService.getHotel(hotel.getHotelId()) ;
		}
		return SUCCESS ;
	}
	
	/**
	 * 发布酒店
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		if(StringUtils.isBlank(hotel.getHotelId())){
			hotel = hotelService.createHotel(hotel) ;
		}else{
			hotel = hotelService.updateHotel(hotel) ;
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
	
	//////////////////////////
	
	
	public HotelDO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDO hotel) {
		this.hotel = hotel;
	}

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	@Override
	public HotelDO getModel() {
		return hotel;
	}

}
