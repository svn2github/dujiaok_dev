package com.ssnn.dujiaok.web.action.order;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.model.AbstractProduct;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.util.order.OrderUtils;
import com.ssnn.dujiaok.util.string.StringUtil;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.ContextHolder;
import com.ssnn.dujiaok.web.context.SessionUtil;

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
		orderDO.setName(product.getName() + "-订单");
		orderDO.setPayType(product.getPayTypes());
		setOrderGmtStartAndEnd(this.orderDO, product);
		
		ProductDetailDO detailDO = this.productDetailService.getProductDetail(this.orderDO.getProductId(),
				this.orderDO.getProductDetailId());
		if (detailDO == null) {			
			return NOT_EXISTS ;
		}
		orderDO.setPrice(getOrderPrice(this.orderDO, detailDO, product));
		
		OrderUtils.setOrderDefaultValue(orderDO);
		
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
			OrderUtils.setSelfDriveOrderEndDateWithStart(orderDO, ((SelfDriveDO) productDO).getDays());
		} else if (productDO.getProductId().startsWith("MP")) {
			OrderUtils.setSelfDriveOrderEndDateWithStart(orderDO, 1);
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
