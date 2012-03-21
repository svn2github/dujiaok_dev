package com.ssnn.dujiaok.web.action.order;

import java.util.Arrays;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.log.DujiaokLogger;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.MemberService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.model.OrderContactDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.util.order.OrderUtils;
import com.ssnn.dujiaok.util.string.StringUtil;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.SessionUtil;

@SuppressWarnings("serial")
public class CopyOfOrderAction extends BasicAction implements ModelDriven<OrderDO> {
	private OrderDO orderDO = new OrderDO();
	
	private SelfDriveService selfDriveService;
	private HotelRoomService hotelRoomService;
	private TicketService ticketService;
	private ProductDetailService productDetailService;
	private MemberService memberService;
	
	private static final DujiaokLogger LOGGER = DujiaokLogger.getLogger(CopyOfOrderAction.class);
	
	public String bookSelfDrive() {
		orderDO.setProductType("SELFDRIVE");
		orderDO.setMemberId(SessionUtil.getLoginMemberDO(getHttpSession()).getMemberId());
		if (orderDO.getGmtOrderStart() == null) {
			LOGGER.error(StringUtil.concat("makeSelfDriveOrder[departDate = null]: wrong date info"));
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的日期有误。");
			return ERROR;
		}
		SelfDriveDO selfDriveDO = this.selfDriveService.getSelfDrive(orderDO.getProductId());
		if (selfDriveDO == null) {
			LOGGER.error(StringUtil.concat("makeSelfDriveOrder[", orderDO.getProductId(), "]: product not Exist"));
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
			return ProductConstant.NOT_EXIST;
		}
		ProductDetailDO detailDO = this.productDetailService.getProductDetail(orderDO.getProductId(), orderDO.getProductDetailId());
		if (detailDO == null) {
			LOGGER.error(StringUtil.concat("makeSelfDriveOrder[", orderDO.getProductId(), "." + orderDO.getProductDetailId(),
										"]: product not Exist"));
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
			return ProductConstant.NOT_EXIST;
		}
		if (orderDO.getGmtOrderStart().before(detailDO.getGmtStart()) || orderDO.getGmtOrderStart().after(detailDO.getGmtEnd())) {
			LOGGER.error(StringUtil.concat("makeSelfDriveOrder[", orderDO.getProductId(), ".", orderDO.getProductDetailId(),
								".", orderDO.getGmtOrderStart(), "]: depart date error"));
			this.getHttpSession().setAttribute("message", "订单信息有误：出行日期无效。");
			return ERROR;
		}
		OrderUtils.setSelfDriveOrderEndDateWithStart(orderDO, selfDriveDO.getDays());
		selfDriveDO.setDetails(Arrays.asList(detailDO));
		
		MemberDO memberDO = this.memberService.queryMember(orderDO.getMemberId());
		OrderContactDO contactor = getDefaultContactor(memberDO);
		orderDO.setContacts(Arrays.asList(contactor));
		
		getHttpSession().setAttribute("product", selfDriveDO);
		getHttpSession().setAttribute("orderDO", orderDO);
		return SUCCESS;
	}
	
	private OrderContactDO getDefaultContactor(MemberDO memberDO) {
		OrderContactDO contactor = new OrderContactDO();
		contactor.setName(memberDO.getNickname());
		contactor.setMobile(memberDO.getMobileNo());
		contactor.setEmail(memberDO.getEmail());
		contactor.setIsMain("Y");
		return contactor;
	}
	
	public String bookHotelRoom() {
		MemberDO memberDO = SessionUtil.getLoginMemberDO(getHttpSession());
		orderDO.setProductType("HOTELROOM");
		orderDO.setMemberId(memberDO.getMemberId());
		if (orderDO.getGmtOrderStart() == null || orderDO.getGmtOrderEnd() == null) {
			LOGGER.error(StringUtil.concat("makeHotelRoomOrder[checkinDate = null, checkoutDate = null]: wrong date info"));
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的日期有误。");
			return ProductConstant.NOT_EXIST;
		}
		HotelRoomDO roomDO = this.hotelRoomService.getRoomWithDetails(orderDO.getProductId());
		if (roomDO == null) {
			LOGGER.error(StringUtil.concat("makeHotelRoomOrder[", orderDO.getProductId(), "]: product not Exist"));
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
			return ProductConstant.NOT_EXIST;
		}
		ProductUtils.filteProductDetail(roomDO.getDetails(), orderDO.getGmtOrderStart(), orderDO.getGmtOrderEnd());
		if (roomDO.getDetails().size() == 0) {
			LOGGER.error(StringUtil.concat("makeHotelRoomOrder[", orderDO.getProductId(), "]: wrong date info"));
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的日期有误。");
			return ProductConstant.NOT_EXIST;
		}
		OrderContactDO contactor = getDefaultContactor(memberDO);
		orderDO.setContacts(Arrays.asList(contactor));
		
		getHttpSession().setAttribute("product", roomDO);
		getHttpSession().setAttribute("orderDO", orderDO);
		return SUCCESS;
	}
	
	
	public String bookTicket() {
		MemberDO memberDO = SessionUtil.getLoginMemberDO(getHttpSession());
		OrderDO orderDO = new OrderDO();
		orderDO.setProductType("TICKET");
		orderDO.setMemberId(memberDO.getMemberId());
		TicketDO ticketDO = this.ticketService.getTicket(orderDO.getProductId());
		if(ticketDO == null) {
			LOGGER.error("makeTicketOrder[" + orderDO.getProductId() + "]: produc not Exist");
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
			return ProductConstant.NOT_EXIST;
		}
		ProductDetailDO detailDO = this.productDetailService.getProductDetail(orderDO.getProductId(), orderDO.getProductDetailId());
		if (detailDO == null) {
			LOGGER.error(StringUtil.concat("makeTicketOrder[", orderDO.getProductId(), "." + orderDO.getProductDetailId(),
					"]: product not Exist"));
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的产品不再销售。");
			return ProductConstant.NOT_EXIST;
		}
		if (orderDO.getGmtOrderStart().before(detailDO.getGmtStart()) || orderDO.getGmtOrderStart().after(detailDO.getGmtEnd())) {
			LOGGER.error(StringUtil.concat("makeHotelRoomOrder[", orderDO.getProductId(), "useDate:",
					orderDO.getGmtOrderStart(), "]: wrong date useDate"));
			this.getHttpSession().setAttribute("message", "非常抱歉，您选择的日期有误。");
			return ProductConstant.NOT_EXIST;
		}
		ticketDO.setDetails(Arrays.asList(detailDO));
		
//		MemberDO memberDO = this.memberService.queryMember(orderDO.getMemberId());
		OrderContactDO contactor = getDefaultContactor(memberDO);
		orderDO.setContacts(Arrays.asList(contactor));
		
		this.getHttpSession().setAttribute("product", ticketDO);
		this.getHttpSession().setAttribute("orderDO", orderDO);
		return SUCCESS;
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

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
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