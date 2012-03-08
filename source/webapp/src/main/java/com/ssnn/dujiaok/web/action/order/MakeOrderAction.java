package com.ssnn.dujiaok.web.action.order;

import java.math.BigDecimal;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.log.DujiaokLogger;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.model.AbstractProduct;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.util.order.OrderUtils;
import com.ssnn.dujiaok.util.string.StringUtil;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.SessionUtil;

@SuppressWarnings("serial")
public class MakeOrderAction extends BasicAction implements ModelDriven<OrderDO> {
	private OrderDO orderDO = new OrderDO();
	
	private OrderService orderService;
	private SelfDriveService selfDriveService;
	private HotelRoomService hotelRoomService;
	private TicketService ticketService;
	private ProductDetailService productDetailService;
	
	private static final DujiaokLogger LOGGER = DujiaokLogger.getLogger(OrderDetailAction.class);
	
	/**
	 * 生成自驾订单.
	 * @return .
	 */
	public String makeOrder() {
		MemberDO memberDO = SessionUtil.getLoginMemberDO(getHttpSession());
		orderDO.setMemberId(memberDO.getMemberId());
		
		if (!OrderUtils.checkOrderKeyInfo(orderDO)) {
			LOGGER.error("Order info error:" + this.orderDO.toString());
			this.getHttpSession().setAttribute("message", "订单信息有误。");
			return ERROR;
		}
		
		AbstractProduct productDo = this.getProduct(orderDO.getProductId(), orderDO.getProductType());
		if (productDo == null) {
			LOGGER.error(StringUtil.concat("makeSelfDriveOrder(productId:", this.orderDO.getProductId(),
					"): product not Exist"));
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
			return ProductConstant.NOT_EXIST;
		}
		orderDO.setName(productDo.getName() + "-订单");
		orderDO.setPayType(productDo.getPayTypes());
		setOrderGmtStartAndEnd(this.orderDO, productDo);
		
		ProductDetailDO detailDO = this.productDetailService.getProductDetail(this.orderDO.getProductId(),
				this.orderDO.getProductDetailId());
		if (detailDO == null) {
			LOGGER.error(StringUtil.concat("makeSelfDriveOrder(productId:", this.orderDO.getProductId(),
					",productDetailId:", this.orderDO.getProductDetailId(),") product not exist."));
			return ProductConstant.NOT_EXIST;
		}
		orderDO.setPrice(getOrderPrice(this.orderDO, detailDO, productDo));
		
		OrderUtils.setOrderDefaultValue(orderDO);
		
		if(StringUtil.isEmpty(orderDO.getOrderId())) {
			try {
				this.orderService.createOrderAndDetailContact(orderDO);
			} catch (Exception e) {
				LOGGER.error("unknow exception", e);
				this.getHttpSession().setAttribute("message", "系统发生未知异常！");
				return ERROR;
			}
		}
		this.getHttpSession().setAttribute("order", orderDO);
		this.getHttpSession().setAttribute("productName", productDo.getName());
		this.getHttpSession().setAttribute("orderDesc", OrderUtils.getOrderInfoDesc(orderDO));
		return SUCCESS;
	}
	
	private AbstractProduct getProduct(String productId, String productType) {
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
	
	private void setOrderGmtStartAndEnd(OrderDO order, AbstractProduct productDO) {
		if (productDO.getProductId().startsWith("ZJ")) {
			OrderUtils.setSelfDriveOrderStartEndDate(orderDO, ((SelfDriveDO) productDO).getDays());
		} else if (productDO.getProductId().startsWith("MP")) {
			OrderUtils.setSelfDriveOrderStartEndDate(orderDO, 1);
		}
	}
	
	private BigDecimal getOrderPrice(OrderDO order, ProductDetailDO detailDO, AbstractProduct productDO) {
		if (order.getProductId().startsWith("ZJ")) {
			int doubleNum = this.orderDO.getCount()/2;
			int days = ((SelfDriveDO) productDO).getDays();
			return detailDO.getDoublePrice().multiply(new BigDecimal(doubleNum)).add(
					detailDO.getPrice().multiply(new BigDecimal(this.orderDO.getCount() % 2))).add(
				    detailDO.getChildPrice().multiply(new BigDecimal(this.orderDO.getSecondaryCount()))).add(
				    new BigDecimal(order.getInsureNum() * 20 * days));
		}
		return detailDO.getPrice().multiply(new BigDecimal(order.getCount()));
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

	public void setProductDetailService(ProductDetailService productDetailService) {
		this.productDetailService = productDetailService;
	}

	@Override
	public OrderDO getModel() {
		return this.orderDO;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}
}
