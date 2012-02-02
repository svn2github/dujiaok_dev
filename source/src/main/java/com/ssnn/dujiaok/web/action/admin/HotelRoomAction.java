package com.ssnn.dujiaok.web.action.admin;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.util.StringListConventUtil;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author shenjia.caosj 2012-1-17
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
				payTypesList = StringListConventUtil.toList(room.getPayTypes()) ;
				imagesList = StringListConventUtil.toList(room.getImages()) ;
				roomFacilitiesList = StringListConventUtil.toList(room.getRoomFacilities()) ;
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
		
		room.setImages(StringListConventUtil.toString(imagesList)) ;
		room.setRoomFacilities(StringListConventUtil.toString(roomFacilitiesList)) ;
		room.setPayTypes(StringListConventUtil.toString(payTypesList)) ;
		
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
