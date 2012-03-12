package com.ssnn.dujiaok.web.action.product;

import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.biz.log.DujiaokLogger;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class DetailAction extends BasicAction {

	private String productId;
	
	private HotelRoomService hotelRoomService ;
	
	private TicketService ticketService;
	
	private SelfDriveService selfDriveService ;
	
	private HotelService hotelService;

	private static final DujiaokLogger LOGGER = DujiaokLogger.getLogger(DetailAction.class);
	@Override
	public String execute() throws Exception {
		
		if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTELROOM)){
			//房间
			return getHotelRoomProductDetail();
		} else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_TICKET)){
			//门票
			return getTicketProductDetail();
		} else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_SELFDRIVE)){
			//自驾
			return getSelfDriveProductDetail();
		} else if (StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTEL)) {
			return getHotelProductDetail();
		} else {
			
		}
		return productNotExist();
	}

	private String getSelfDriveProductDetail() {
		try {
			SelfDriveDO param = new SelfDriveDO();
			param.setProductId(this.productId);
			SelfDriveDO product = this.selfDriveService.getSelfDriveWithDetails(param.getProductId());
			if (product == null) {
				return productNotExist();
			}
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			this.getHttpSession().setAttribute("product", product);
			return ProductConstant.SELF_DRIVE;
		} catch (Exception e) {
			LOGGER.error("Get SelfDriveProduct[" + this.productId + "] error", e);
			return ERROR;
		}
	}
	
	private String getHotelRoomProductDetail() {
		try {
			HotelRoomDO param = new HotelRoomDO();
			param.setProductId(this.productId);
			HotelRoomDO product = this.hotelRoomService.getRoomWithDetails(param.getProductId());
			if (product == null) {
				return productNotExist();
			}
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			HotelDO hotelDO = hotelService.getHotel(product.getHotelId());
			String hotelName = (hotelDO == null) ? null : hotelDO.getName();
			this.getHttpSession().setAttribute("product", product);
			this.getHttpSession().setAttribute("hotelName", hotelName);
			return ProductConstant.ROOM;
		} catch (Exception e) {
			LOGGER.error("Get HotelRoomProduct[" + this.productId + "] error", e);
			return ERROR;
		}
	}
	
	private String getTicketProductDetail() {
		try {
			TicketDO param = new TicketDO();
			param.setProductId(this.productId);
			TicketDO product = this.ticketService.getTicketWithDetails(param.getProductId());
			if (product == null) {
				return productNotExist();
			}
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			this.getHttpSession().setAttribute("product", product);
			return ProductConstant.TICKET;
		} catch (Exception e) {
			LOGGER.error("Get TicketProduct[" + this.productId + "] error", e);
			return ERROR;
		}
	}
	
	private String getHotelProductDetail() {
		try {
			HotelDO hotelDO = this.hotelService.getHotel(this.productId);
			if (hotelDO == null) {
				return productNotExist();
			}
			this.getHttpSession().setAttribute("product", hotelDO);
			return ProductConstant.HOTEL;
		} catch(Exception e) {
			LOGGER.error(e);
			return ERROR;
		}
	}
	
	private String productNotExist() {
		LOGGER.error("TicketProduct[" + this.productId + "] not exist", null);
		this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
		return ProductConstant.NOT_EXIST;
	}
	
	public String getProductId() {
		return productId;
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

	public HotelService getHotelService() {
		return hotelService;
	}

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}
}