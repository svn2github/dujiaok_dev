package com.ssnn.dujiaok.web.action.admin;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.util.ArrayStringUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author langben 2012-1-17
 *
 */
@SuppressWarnings("serial")
public class HotelAction extends BasicAction implements ModelDriven<HotelDO>{

	private HotelDO hotel = new HotelDO() ;
	
	private List<String> imagesList ;
	
	private HotelService hotelService ;
	
	@Override
	public String execute() throws Exception {
		if(hotel!=null && StringUtils.isNotBlank(hotel.getProductId())){
			hotel = hotelService.getHotel(hotel.getProductId()) ;
			if(hotel != null){
				imagesList = ArrayStringUtils.toList(hotel.getImages()) ;
			}
		}
		return SUCCESS ;
	}
	
	/**
	 * 发布酒店
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		hotel.setImages(ArrayStringUtils.toString(imagesList)) ;
		if(StringUtils.isBlank(hotel.getProductId())){
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

	public List<String> getImagesList() {
		return imagesList;
	}

	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
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
