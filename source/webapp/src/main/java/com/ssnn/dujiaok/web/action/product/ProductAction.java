package com.ssnn.dujiaok.web.action.product;

import com.ssnn.dujiaok.biz.log.DujiaokLogger;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class ProductAction extends BasicAction {
	private String productId;
	
	private SelfDriveService selfDriveService;
	private HotelService hotelService;
	private HotelRoomService hotelRoomService;
	private TicketService ticketService;
	
	private static final DujiaokLogger LOGGER = DujiaokLogger.getLogger(ProductAction.class);

	public String getSelfDriveProductDetail() {
		try {
			SelfDriveDO param = new SelfDriveDO();
			param.setProductId(this.productId);
//			param.setProductId("ZJ1201201447352717");
			SelfDriveDO product = this.selfDriveService.getSelfDriveWithDetails(param.getProductId());
			if (product == null) {
				LOGGER.error("SelfDriveProduct[" + this.productId + "] not exist", null);
				this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
				return ProductConstant.NOT_EXIST;
			}
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error("Get SelfDriveProduct[" + this.productId + "] error", e);
			return ERROR;
		}
	}
	
	public String getHotelProductDetail() {
		try {
			HotelDO param = new HotelDO();
//			param.setProductId(this.productId);
			param.setProductId("JD1201171609535427");
			HotelDO product = this.hotelService.getHotel(param.getProductId());
			if (product == null) {
				LOGGER.error("HotelProduct[" + this.productId + "] not exist", null);
				this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
				return ProductConstant.NOT_EXIST;
			}
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error("Get HotelProduct[" + this.productId + "] error", e);
			return ERROR;
		}
	}
	
	public String getHotelRoomProductDetail() {
		try {
			HotelRoomDO param = new HotelRoomDO();
			//param.setProductId(this.productId);
			param.setProductId("FJ1201181530466890");
			HotelRoomDO product = this.hotelRoomService.getRoomWithDetails(param.getProductId());
			if (product == null) {
				LOGGER.error("HotelRoomProduct[" + this.productId + "] not exist", null);
				this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
				return ProductConstant.NOT_EXIST;
			}
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error("Get HotelRoomProduct[" + this.productId + "] error", e);
			return ERROR;
		}
	}
	
	public String getTicketProductDetail() {
		try {
			TicketDO param = new TicketDO();
			//param.setProductId(this.productId);
			param.setProductId("MP1202120411482768");
			TicketDO product = this.ticketService.getTicketWithDetails(param.getProductId());
			if (product == null) {
				LOGGER.error("TicketProduct[" + this.productId + "] not exist", null);
				this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
				return ProductConstant.NOT_EXIST;
			}
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error("Get TicketProduct[" + this.productId + "] error", e);
			return ERROR;
		}
	}
	
	public String getProductId() {
		return productId.toString();
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
}