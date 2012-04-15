package com.ssnn.dujiaok.web.action.order;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.model.AbstractProduct;
import com.ssnn.dujiaok.model.DetailItemDO;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.util.enums.ProductEnums;
import com.ssnn.dujiaok.util.order.OrderUtils;
import com.ssnn.dujiaok.util.string.StringUtil;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.ContextHolder;

@SuppressWarnings("serial")
public class MakeOrderAction extends BasicAction implements ModelDriven<OrderDO> {
	
	private OrderDO orderDO = new OrderDO();
	
	private AbstractProduct product ;
	
	private OrderService orderService;
	
	private SelfDriveService selfDriveService;
	
	private HotelRoomService hotelRoomService;
	
	private TicketService ticketService;
	
	private ProductDetailService productDetailService;
	
	private String orderDesc ;
	
	private static final Log log = LogFactory.getLog(OrderDetailAction.class);
	
	public String makeOrder() {
		String memberId = ContextHolder.getMemberContext().getMemberId() ;
		
		orderDO.setMemberId(memberId);
		
		if (!OrderUtils.checkOrderKeyInfo(orderDO)) {
			return INPUT ;
		}
		
		product = this.getProduct(orderDO.getProductId());
		if (product == null) {
			return NOT_EXISTS ;
		}
		orderDO.setName(product.getName() + " - 订单");
		orderDO.setPayType(product.getPayTypes());
		setOrderGmtStartAndEnd(this.orderDO, product);
		
		ProductDetailDO detailDO = this.productDetailService.getProductDetail(this.orderDO.getProductId(),
				this.orderDO.getProductDetailId());
		if (detailDO == null) {			
			return NOT_EXISTS ;
		}
		orderDO.setPrice(getOrderPrice(this.orderDO, product, detailDO));
		
		OrderUtils.setOrderDefaultValue(orderDO);
		orderDO.setProductType(ProductEnums.fromProductId(product.getProductId()).getName()) ;
		if(StringUtil.isEmpty(orderDO.getOrderId())) {
			try {
				this.orderService.insertOrderAndDetailContact(orderDO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return ERROR;
			}
		}
		
		orderDesc = OrderUtils.getOrderInfoDesc(orderDO) ;
		
		return SUCCESS;
	}
	
	private AbstractProduct getProduct(String productId) {
		if (productId.startsWith("ZJ")) {
			return this.selfDriveService.getSelfDrive(productId);
		} else if (productId.startsWith("MP")) {
			return this.ticketService.getTicket(productId);
		} else if (productId.startsWith("FJ")) {
			return this.hotelRoomService.getRoom(productId);
		} 
		return null;
	}
	
	private void setOrderGmtStartAndEnd(OrderDO order, AbstractProduct productDO) {
		if (productDO.getProductId().startsWith("ZJ")) {
			OrderUtils.setOrderEndDateWithStart(orderDO, ((SelfDriveDO) productDO).getDays());
		} else if (productDO.getProductId().startsWith("MP")) {
			OrderUtils.setOrderEndDateWithStart(orderDO, 1);
		}
	}
	
	private BigDecimal getOrderPrice(OrderDO order, AbstractProduct productDO, ProductDetailDO detailDO) {
		if (StringUtils.startsWithIgnoreCase(order.getProductId(), Constant.PREFIX_SELFDRIVE)) {
			int doubleNum = this.orderDO.getCount()/2;
			int days = ((SelfDriveDO) productDO).getDays();
			return detailDO.getDoublePrice().multiply(new BigDecimal(doubleNum)).add(
					detailDO.getPrice().multiply(new BigDecimal(this.orderDO.getCount() % 2))).add(
				    detailDO.getChildPrice().multiply(new BigDecimal(this.orderDO.getSecondaryCount()))).add(
				    new BigDecimal(order.getInsureNum() * 20 * days));
		} else if (StringUtils.startsWithIgnoreCase(order.getProductId(), Constant.PREFIX_TICKET)) {
			return detailDO.getPrice().multiply(new BigDecimal(order.getCount()));
		} else if (StringUtils.startsWithIgnoreCase(order.getProductId(), Constant.PREFIX_HOTELROOM)){
			HotelRoomDO roomDO = this.hotelRoomService.getRoomWithDetails(order.getProductId());
			BigDecimal roomPrice = OrderUtils.getRoomPrice(roomDO.getDefaultDetailItems(),
					order.getGmtOrderStart(), order.getGmtOrderEnd());
			return roomPrice.multiply(new BigDecimal(order.getCount()));
		} else {
			return null; 
		}
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

	public AbstractProduct getProduct() {
		return product;
	}

	public void setProduct(AbstractProduct product) {
		this.product = product;
	}

	public String getOrderDesc() {
		return orderDesc;
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
