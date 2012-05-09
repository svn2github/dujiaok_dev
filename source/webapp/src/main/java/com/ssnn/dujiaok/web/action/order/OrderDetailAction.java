package com.ssnn.dujiaok.web.action.order;

import com.ssnn.dujiaok.biz.log.DujiaokLogger;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.model.AbstractProduct;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.util.order.OrderUtils;
import com.ssnn.dujiaok.util.string.StringUtil;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class OrderDetailAction extends BasicAction {
	private String orderId;
	
	private OrderService orderService;
	private SelfDriveService selfDriveService;
	private HotelRoomService hotelRoomService;
	private TicketService ticketService;
	
	private OrderDO orderDO ;
	
	private AbstractProduct product ;
	
	private String orderDesc ;
	
	private static final DujiaokLogger LOGGER = DujiaokLogger.getLogger(OrderDetailAction.class);

	public String orderDetail() {
		if (StringUtil.isEmpty(this.orderId)) {
			return notExistError();
		}
	
		orderDO = orderService.getOrderAndDetailContact(this.orderId);
		if (orderDO == null) {
			return notExistError();
		}
		product = this._getProduct(orderDO.getProductId(), orderDO.getProductType());
		if (product == null) {
			LOGGER.error(StringUtil.concat("orderDetail(orderId:", this.orderId, "): Can't find product"));
		}
		
		orderDesc = OrderUtils.getOrderInfoDesc(orderDO) ;
		return SUCCESS;
	}

	private AbstractProduct _getProduct(String productId, String productType) {
		if (productId.startsWith("ZJ")) {
			return this.selfDriveService.getSelfDrive(productId);
		} else if (productId.startsWith("MP")) {
			return this.ticketService.getTicket(productId);
		} else if (productId.startsWith("FJ")) {
			return this.hotelRoomService.getRoom(productId);
		} else {
			if ("SELFDRIVE".equals(productType)) {
				return this.selfDriveService.getSelfDrive(productId);
			} else if ("TICKET".equals(productType)) {
				return this.ticketService.getTicket(productId);
			} else if ("HOTELROOM".equals(productType)) {
				return this.hotelRoomService.getRoom(productId);
			}
		}
		return null;
	}
	
	private String notExistError() {
		LOGGER.error("orderId error: " + orderId);
		getHttpSession().setAttribute("message", "没有相关订单信息。");
		return ProductConstant.NOT_EXIST;
	}
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setSelfDriveService(SelfDriveService selfDriveService) {
		this.selfDriveService = selfDriveService;
	}
	public void setHotelRoomService(HotelRoomService hotelRoomService) {
		this.hotelRoomService = hotelRoomService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public AbstractProduct getProduct() {
		return product;
	}

	public String getOrderDesc() {
		return orderDesc;
	}
	
	
}
