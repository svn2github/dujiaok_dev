package com.ssnn.dujiaok.web.action.order;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.MemberService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.constant.ProductConstant;
import com.ssnn.dujiaok.model.AbstractProduct;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.model.OrderContactDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.util.enums.ProductEnums;
import com.ssnn.dujiaok.util.order.OrderUtils;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.ContextHolder;

@SuppressWarnings("serial")
public class OrderAction extends BasicAction implements ModelDriven<OrderDO> {
	private OrderDO orderDO = new OrderDO();
	
	private AbstractProduct product ;
	
		
	private SelfDriveService selfDriveService;
	private HotelRoomService hotelRoomService;
	private TicketService ticketService;
	private ProductDetailService productDetailService;
	private MemberService memberService;
	
	private static final Log LOGGER = LogFactory.getLog(OrderAction.class);
	
	public String book(){
		AbstractProduct product = null ;
		
		String memberId = ContextHolder.getMemberContext().getMemberId() ;
		
		ProductEnums type = ProductEnums.fromValue(orderDO.getProductType()) ;
		String productId = orderDO.getProductId() ;
		if(type == ProductEnums.SELFDRIVE){
			product = selfDriveService.getSelfDriveWithDetails(productId) ;
		}else if(type == ProductEnums.HOTEL_ROOM){
			product = hotelRoomService.getRoomWithDetails(productId) ;
		}else if(type == ProductEnums.TICKET){
			product = ticketService.getTicketWithDetails(productId) ;
		}
		if(product == null){
			return ProductConstant.NOT_EXIST ;
		}
		String detailId = orderDO.getProductDetailId() ;
		ProductDetailDO detail = productDetailService.getProductDetail(productId , detailId);
		if(detail == null){
			return ProductConstant.NOT_EXIST ;
		}
		
		//gmtStart 有问题
		if (orderDO.getGmtOrderStart()==null || orderDO.getGmtOrderStart().before(detail.getGmtStart()) || orderDO.getGmtOrderStart().after(detail.getGmtEnd())) {
			return ProductConstant.NOT_EXIST;
		}
		//设置自驾时间
		if(ProductEnums.SELFDRIVE == type){
			SelfDriveDO s = (SelfDriveDO)product ;
			OrderUtils.setSelfDriveOrderEndDateWithStart(orderDO, s.getDays());
		}
		
		//设置首要联系人
		MemberDO member = memberService.queryMember(memberId) ;
		OrderContactDO contactor = getDefaultContactor(member);
		orderDO.setContacts(Arrays.asList(contactor));
		
		//计算价格
		this.product = product ;
		
		if(type == ProductEnums.SELFDRIVE){
			return ProductConstant.SELF_DRIVE ;
		}else if(type == ProductEnums.HOTEL_ROOM){
			return ProductConstant.ROOM ;
		}else if(type == ProductEnums.TICKET){
			return ProductConstant.TICKET ;
		}
		return ProductConstant.NOT_EXIST ;
	}
	
	
	private OrderContactDO getDefaultContactor(MemberDO memberDO) {
		if(memberDO == null){
			return null ;
		}
		OrderContactDO contactor = new OrderContactDO();
		contactor.setName(memberDO.getNickname());
		contactor.setMobile(memberDO.getMobileNo());
		contactor.setEmail(memberDO.getEmail());
		contactor.setIsMain("T");
		return contactor;
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

	public AbstractProduct getProduct() {
		return product;
	}


	public void setProduct(AbstractProduct product) {
		this.product = product;
	}


	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}
}