package com.ssnn.dujiaok.web.action.order;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.model.OrderContactDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.order.OrderUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

public class OrderDetailAction extends BasicAction {
	private String productId;
	private String productDetailId;
	private Integer adultNum;
	private Integer childNum;
	private Integer insuranceNum;
	private Date departDate;
	
	private Integer ticketNum;
	private String ticketUseDate;
	
	private String checkInDate;
	
	private OrderService orderService;
	private SelfDriveService selfDriveService;
	private HotelService hotelService;
	private HotelRoomService hotelRoomService;
	private TicketService ticketService;
	private ProductDetailService productDetailService;
	
	/**
	 * 生成自驾订单.
	 * @return .
	 */
	public String makeSelfDriveOrder() {
		OrderDO orderDO = new OrderDO();
		orderDO.setProductId(this.productId);
		orderDO.setProductDetailId(this.productDetailId);
		//TODO get memberid
		//orderDO.setMemberId(memberId);
		orderDO.setProductType("1");
		orderDO.setCount(this.adultNum);
		orderDO.setSecondaryCount(this.childNum);
		orderDO.setGmtOrderStart(this.departDate);
		orderDO.setMemberId("hello1235");
		
		ProductDetailDO detailDO = this.productDetailService.getProductDetail(this.productId, this.productDetailId);
		if (detailDO == null) {
			//LOG can't locate selfdrive product detail difinitely error;
			return ERROR;
		}
		int doubleNum = this.adultNum/2;
		BigDecimal totalPrice = detailDO.getDoublePrice().multiply(new BigDecimal(doubleNum)).add(
				detailDO.getPrice().multiply(new BigDecimal(this.adultNum % 2))).add(
			    detailDO.getChildPrice().multiply(new BigDecimal(this.childNum)));
		orderDO.setPrice(totalPrice);
		SelfDriveDO productDo = this.selfDriveService.getSelfDrive(this.productId);
		if (productDo == null) {
			//TODO log can't find product error;
			return ERROR;
		}
		orderDO.setName(productDo.getName() + "-订单");
		orderDO.setPayType(productDo.getPayTypes());
		OrderUtils.setSelfDriveOrderStartEndDate(orderDO, productDo.getDays());
		
		List<OrderContactDO> contacts = null;
		orderDO.setContacts(contacts);
		//set order default value
		OrderUtils.setOrderDefaultValue(orderDO);
		if (!OrderUtils.checkOrderKeyInfo(orderDO)) {
			return ERROR;
		}
		try {
			OrderDO orderDO2 = this.orderService.createOrderAndDetailContact(orderDO);
		} catch (Exception e) {
			//TODO log error;
			return ERROR;
		}
		this.getHttpSession().setAttribute("order", orderDO);
		this.getHttpSession().setAttribute("productName", productDo.getName());
		return SUCCESS;
	}
	
	private void setNewOrderStatus(OrderDO orderDO) {
		orderDO.setStatus("待付款");
		orderDO.setStatusDetail("下单待付款");
		orderDO.setPayStatus("UNPAY");
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(String productDetailId) {
		this.productDetailId = productDetailId;
	}

	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}

	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}

	public Integer getInsuranceNum() {
		return insuranceNum;
	}

	public void setInsuranceNum(Integer insuranceNum) {
		this.insuranceNum = insuranceNum;
	}

	public Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(String departDate) {
		DateFormat tempFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.departDate = tempFormat.parse(departDate);
		} catch (Exception e) {
			this.departDate = null;
		}
	}

	public Integer getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Integer ticketNum) {
		this.ticketNum = ticketNum;
	}

	public String getTicketUseDate() {
		return ticketUseDate;
	}

	public void setTicketUseDate(String ticketUseDate) {
		this.ticketUseDate = ticketUseDate;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
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

	public void setProductDetailService(ProductDetailService productDetailService) {
		this.productDetailService = productDetailService;
	}
	
}
