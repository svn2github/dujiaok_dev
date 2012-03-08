package com.ssnn.dujiaok.web.action.product;

import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.biz.log.DujiaokLogger;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class DetailAction extends BasicAction {

	private Object product;

	private String productId;
	
	private HotelRoomService hotelRoomService ;
	
	private TicketService ticketService;
	
	private SelfDriveService selfDriveService ;

	private static final DujiaokLogger LOGGER = DujiaokLogger.getLogger(DetailAction.class);
	@Override
	public String execute() throws Exception {
		
		if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_HOTELROOM)){
			//房间
			return getHotelRoomProductDetail();
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_TICKET)){
			//门票
			return getTicketProductDetail();
		}else if(StringUtils.startsWithIgnoreCase(productId, Constant.PREFIX_SELFDRIVE)){
			//自驾
			return getSelfDriveProductDetail();
		}
		
		return ProductConstant.NOT_EXIST;
	}

	private String getSelfDriveProductDetail() {
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
			return ProductConstant.SELF_DRIVE;
		} catch (Exception e) {
			LOGGER.error("Get SelfDriveProduct[" + this.productId + "] error", e);
			return ERROR;
		}
	}
	
	private String getHotelRoomProductDetail() {
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
//			param.setProductId("MP1202120411482768");
			TicketDO product = this.ticketService.getTicketWithDetails(param.getProductId());
			if (product == null) {
				LOGGER.error("TicketProduct[" + this.productId + "] not exist", null);
				this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
				return ProductConstant.NOT_EXIST;
			}
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			this.getHttpSession().setAttribute("product", product);
			return ProductConstant.TICKET;
		} catch (Exception e) {
			LOGGER.error("Get TicketProduct[" + this.productId + "] error", e);
			return ERROR;
		}
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
}