package com.ssnn.dujiaok.web.action.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.model.AbstractProduct;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.util.ArrayStringUtils;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class DetailAction extends BasicAction implements ModelDriven<Pagination> {

	private Object product;

	private String productId;
	
	private String hotelName ;
	
	private HotelRoomService hotelRoomService ;
	
	private TicketService ticketService;
	
	private SelfDriveService selfDriveService ;
	
	private HotelService hotelService ;
	
	private List<String> imageList ;

	private QueryResult<HotelRoomDO> roomList ;
	
	private Pagination pagination = new Pagination(1,5);
	
	private static final Log  LOGGER = LogFactory.getLog(DetailAction.class);
	
	@Override
	public String execute() throws Exception {
		
		String result = ProductConstant.NOT_EXIST;
		
		if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTELROOM)){
			//房间
			product = hotelRoomService.getRoomWithDetails(productId);
			result = ProductConstant.ROOM ;
			if(product != null){
				HotelRoomDO room = (HotelRoomDO)product ;
				HotelDO hotel = hotelService.getHotel(room.getHotelId()) ;
				if(hotel != null){
					hotelName  = hotel.getName() ;
				}
			}
			
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_TICKET)){
			//门票
			product =  ticketService.getTicketWithDetails(productId);
			result = ProductConstant.TICKET ;			
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_SELFDRIVE)){
			//自驾
			product = selfDriveService.getSelfDriveWithDetails(productId);
			result = ProductConstant.SELF_DRIVE ;
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTEL)){
			//JIUDIAN
			product = hotelService.getHotel(productId);
			Map<String,Object> condition = new HashMap<String,Object>() ;
			condition.put("hotelId", productId) ;
			roomList = hotelRoomService.getRooms(condition, pagination) ;
			result = ProductConstant.HOTEL ;
		}
		
		if(product != null && product instanceof AbstractProduct){
			AbstractProduct aProduct = (AbstractProduct)product ;
			imageList = ArrayStringUtils.toList(aProduct.getImages()) ;
			ProductUtils.filteInvalideProductDetail(aProduct.getDetails()) ;
			return result ;
		}
				
		return ProductConstant.NOT_EXIST;
	}
	
	
	/**
	 * ----------------------------------------------------------------------------
	 * @return
	 */

	public List<String> getImageList() {
		return imageList;
	}


	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}


	public Pagination getPagination() {
		return pagination;
	}


	public QueryResult<HotelRoomDO> getRoomList() {
		return roomList;
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

	public String getHotelName() {
		return hotelName;
	}


	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}


	@Override
	public Pagination getModel() {
		return pagination ;
	}	
	
}