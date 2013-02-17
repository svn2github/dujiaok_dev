package com.ssnn.dujiaok.web.action.admin;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.util.ArrayStringUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author langben 2012-1-17
 *
 */
@SuppressWarnings("serial")
public class HotelRoomAction extends BasicAction implements ModelDriven<HotelRoomDO>{

	private HotelRoomDO room = new HotelRoomDO() ;
	
	private List<String> payTypesList ;
	private List<String> imagesList ;
	private List<String> roomFacilitiesList ;
	
	private HotelRoomService hotelRoomService ;
	
	@Override
	public String execute() throws Exception {
		if(room!=null && StringUtils.isNotBlank(room.getProductId())){
			room = hotelRoomService.getRoomWithDetails(room.getProductId()) ;
			if(room != null){
				payTypesList = ArrayStringUtils.toList(room.getPayTypes()) ;
				imagesList = ArrayStringUtils.toList(room.getImages()) ;
				roomFacilitiesList = ArrayStringUtils.toList(room.getRoomFacilities()) ;
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
		
		room.setImages(ArrayStringUtils.toString(imagesList)) ;
		room.setRoomFacilities(ArrayStringUtils.toString(roomFacilitiesList)) ;
		room.setPayTypes(ArrayStringUtils.toString(payTypesList)) ;
		
		if(StringUtils.isBlank(room.getProductId())){
			room = hotelRoomService.createRoomAndDetails(room) ;
		}else{
			room = hotelRoomService.updateRoomAndDetails(room) ;
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
	
	
	@Override
	public HotelRoomDO getModel() {
		return room ;
	}

	/**----------------------------------------------------------------------**/
	
	
	public HotelRoomDO getRoom() {
		return room;
	}

	public void setRoom(HotelRoomDO room) {
		this.room = room ;
	}

	public List<String> getPayTypesList() {
		return payTypesList;
	}

	public void setPayTypesList(List<String> payTypesList) {
		this.payTypesList = payTypesList;
	}

	public List<String> getImagesList() {
		return imagesList;
	}

	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}
	
	public List<String> getRoomFacilitiesList() {
		return roomFacilitiesList;
	}

	public void setRoomFacilitiesList(List<String> roomFacilitiesList) {
		this.roomFacilitiesList = roomFacilitiesList;
	}

	public void setHotelRoomService(HotelRoomService hotelRoomService) {
		this.hotelRoomService = hotelRoomService;
	}
	
	

}
