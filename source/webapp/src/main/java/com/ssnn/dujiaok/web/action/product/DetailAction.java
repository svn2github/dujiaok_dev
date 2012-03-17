package com.ssnn.dujiaok.web.action.product;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.model.AbstractProduct;
import com.ssnn.dujiaok.util.ArrayStringUtils;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class DetailAction extends BasicAction {

	private Object product;

	private String productId;
	
	private HotelRoomService hotelRoomService ;
	
	private TicketService ticketService;
	
	private SelfDriveService selfDriveService ;
	
	private HotelService hotelService ;
	
	private List<String> imageList ;

	private static final Log  LOGGER = LogFactory.getLog(DetailAction.class);
	@Override
	public String execute() throws Exception {
		
		String result = ProductConstant.NOT_EXIST;
		
		if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTELROOM)){
			//房间
			product = hotelRoomService.getRoomWithDetails(productId);
			result = ProductConstant.ROOM ;
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_TICKET)){
			//门票
			product =  ticketService.getTicketWithDetails(productId);
			result = ProductConstant.TICKET ;			
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_SELFDRIVE)){
			//自驾
			product = selfDriveService.getSelfDriveWithDetails(productId);
			result = ProductConstant.SELF_DRIVE ;
		}
//		else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTEL)){
//			if(product != null){
//				product = hotelService.getHotel(productId);
//			}
//			return ProductConstant.HOTEL ;
//		}
		
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