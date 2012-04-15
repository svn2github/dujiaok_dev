package com.ssnn.dujiaok.web.action.order;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class OrderAction extends BasicAction  {
	
	private static final Log log = LogFactory.getLog(OrderAction.class) ;
	private static final String DATEFORMAT = "yyyy-MM-dd" ;
	/**
	 * 游玩时间和detailId
	 * detailId:yyyy-MM-dd
	 */
	private String dateAndDetail ;
	
	private String endDate ;
	
	private int count ;
	
	private int secondaryCount ;
	
	private String productType ;
	
	private String productId ;
	
	private String roomPrice;
	
	private String markeyRoomPrice;
	
	private OrderDO orderDO = new OrderDO();
	
	private AbstractProduct product ;
			
	private SelfDriveService selfDriveService;
	private HotelRoomService hotelRoomService;
	private TicketService ticketService;
	private ProductDetailService productDetailService;
	private MemberService memberService;
	
	//当前的detail
	private ProductDetailDO detail ;
	
	public String book(){
		AbstractProduct product = null ;
		String memberId = ContextHolder.getMemberContext().getMemberId() ;
		ProductEnums type = ProductEnums.fromValue(productType) ;
		
		if(type == ProductEnums.SELFDRIVE){
			product = selfDriveService.getSelfDriveWithDetails(productId) ;
		}else if(type == ProductEnums.HOTEL_ROOM){
			product = hotelRoomService.getRoomWithDetails(productId) ;
		}else if(type == ProductEnums.TICKET){
			product = ticketService.getTicketWithDetails(productId) ;
		}
		if(product == null){
			return NOT_EXISTS ;
		}
		
		String[] dateAndDetailArr = StringUtils.split(dateAndDetail,":") ;
		if(dateAndDetailArr == null || dateAndDetailArr.length != 2){
			return NOT_EXISTS ;
		}
		String detailId = dateAndDetailArr[0] ;
		Date gmtOrderStart = null ;
		
		DateFormat df = new SimpleDateFormat(DATEFORMAT) ;
		try {
			gmtOrderStart = df.parse(dateAndDetailArr[1]) ;
		} catch (ParseException e) {
			log.error(e.getMessage() , e) ;
		}
		
		this.detail = productDetailService.getProductDetail(productId , detailId);
		
		if(detail == null){
			return NOT_EXISTS ;
		}
				
		//gmtStart 有问题
		if (gmtOrderStart==null || gmtOrderStart.before(detail.getGmtStart()) || gmtOrderStart.after(detail.getGmtEnd())) {
			return NOT_EXISTS ;
		}
		//设置自驾时间
		if(ProductEnums.SELFDRIVE == type){
			SelfDriveDO s = (SelfDriveDO)product ;
			Date gmtOrderEnd = org.apache.commons.lang.time.DateUtils.addDays(gmtOrderStart, s.getDays()) ;
			orderDO.setGmtOrderEnd(gmtOrderEnd) ;
		}
		if(endDate != null){
			try {
				Date gmtOrderEnd = df.parse(endDate) ;
				orderDO.setGmtOrderEnd(gmtOrderEnd) ;
			} catch (ParseException e) {
				log.error(e.getMessage() , e) ;
			}
		}
		
		//计算价格
		this.product = product ;
		
		//设置首要联系人
		MemberDO member = memberService.queryMember(memberId) ;
		OrderContactDO contactor = getDefaultContactor(member);
		orderDO.setContacts(Arrays.asList(contactor));
		
		
		//设置Order属性
		orderDO.setCount(count) ;
		orderDO.setSecondaryCount(secondaryCount) ;
		orderDO.setGmtOrderStart(gmtOrderStart) ;
		orderDO.setProductDetailId(detailId) ;
		orderDO.setProductId(productId) ;
		
		if(type == ProductEnums.HOTEL_ROOM) {
			BigDecimal[] roomPrices = OrderUtils.getRoomPrice(product,
					orderDO.getGmtOrderStart(), orderDO.getGmtOrderEnd());
			this.roomPrice = roomPrices[0].toString();
			this.markeyRoomPrice = roomPrices[1].toString();
			
		}
		if(type == ProductEnums.SELFDRIVE){
			return ProductConstant.SELF_DRIVE ;
		}else if(type == ProductEnums.HOTEL_ROOM){
			return ProductConstant.ROOM ;
		}else if(type == ProductEnums.TICKET){
			return ProductConstant.TICKET ;
		}
		return NOT_EXISTS ;
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
	
	public void setProductType(String productType) {
		this.productType = productType;
	}


	public ProductDetailDO getDetail() {
		return detail;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public void setDateAndDetail(String dateAndDetail) {
		this.dateAndDetail = dateAndDetail;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public void setSecondaryCount(int secondaryCount) {
		this.secondaryCount = secondaryCount;
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

	
	public OrderDO getOrderDO() {
		return orderDO;
	}

	public AbstractProduct getProduct() {
		return product;
	}


	public void setProduct(AbstractProduct product) {
		this.product = product;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getRoomPrice() {
		return roomPrice;
	}


	public void setRoomPrice(String roomPrice) {
		this.roomPrice = roomPrice;
	}


	public String getMarkeyRoomPrice() {
		return markeyRoomPrice;
	}


	public void setMarkeyRoomPrice(String markeyRoomPrice) {
		this.markeyRoomPrice = markeyRoomPrice;
	}

}