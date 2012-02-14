package com.ssnn.dujiaok.web.action.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.MemberService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.util.order.OrderUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

public class MakeOrderAction extends BasicAction {
	/**
	 * 产品id
	 */
	private String productId;
	/**
	 * 产品详单id.
	 */
	private String productDetailId;
	/**
	 * 自驾成人数.
	 */
	private Integer adultNum;
	/**
	 * 自驾孩童数.
	 */
	private Integer childNum;
	/**
	 * 自驾出行日期.
	 */
	private Date departDate;
	
	/**
	 * 入住日期.酒店.
	 */
	private Date checkinDate;
	/**
	 * 离店日期.酒店
	 */
	private Date checkoutDate;
	/**
	 * 门票数量.
	 */
	private Integer ticketNum;
	
//	private TourService tourService;
//	private ProductService productService;
	
	private SelfDriveService selfDriveService;
	private HotelService hotelService;
	private HotelRoomService hotelRoomService;
	private TicketService ticketService;
	private ProductDetailService productDetailService;
	private MemberService memberService;
	
	@Override
	public String execute() throws Exception  {
//        int countTotal = this.adultNum + this.childNum;
//
//        Tour tour = tourService.getTourById(new Integer(productDetailId));
//
//        if (tour == null || tour.getStartDate() == null) {
//            return SUCCESS;
//        }
//
//        if (tour.getChildPrice() == null) {
//        	getHttpSession().setAttribute("childPrice", 0);
//        } else {
//        	getHttpSession().setAttribute("childPrice", tour.getChildPrice());
//        }
//
//        if (tour.getDoublePrice() == null) {
//        	getHttpSession().setAttribute("doublePrice", 0);
//        } else {
//        	getHttpSession().setAttribute("doublePrice", tour.getDoublePrice());
//        }
//
//        if (tour.getSinglePrice() == null) {
//        	getHttpSession().setAttribute("singlePrice", 0);
//        } else {
//        	getHttpSession().setAttribute("singlePrice", tour.getSinglePrice());
//        }
//        
//        if (tour.getComboPrice() == null) {
//            getHttpSession().setAttribute("comboPrice", 0);
//        } else {
//        	getHttpSession().setAttribute("comboPrice", tour.getComboPrice());
//        }
//        
//        Product product = productService.getProductById(new Integer(productId));
//        if(product != null){
//        	getHttpSession().setAttribute("product", product);
//        }
//
//        getHttpSession().setAttribute("day", tour.getStartDate());
//        getHttpSession().setAttribute("dest", product.getDest());
//        getHttpSession().setAttribute("countPerson", this.adultNum);
//        getHttpSession().setAttribute("countChild", this.childNum);
//        getHttpSession().setAttribute("countTotal", countTotal);
//        getHttpSession().setAttribute("productId", productId);
        return SUCCESS;
	}
	
	public String makeSelfDriveOrder() {
		OrderDO orderDO = new OrderDO();
		//TODO
		orderDO.setProductType("1");
		orderDO.setProductId(this.productId);
		//TODO
		orderDO.setProductDetailId(this.productDetailId);
		//TODO
		orderDO.setMemberId("hello1235");
		orderDO.setCount(this.adultNum == null? 0: this.adultNum);
		orderDO.setSecondaryCount(this.childNum == null ? 0 : this.childNum);
		orderDO.setGmtOrderStart(this.departDate);
		SelfDriveDO selfDriveDO = this.selfDriveService.getSelfDrive(this.productId);
		if (selfDriveDO == null) {
			//TODO log no detail product error
			return ERROR;
		}
		ProductDetailDO detailDO = this.productDetailService.getProductDetail(this.productId, this.productDetailId);
		if (detailDO == null) {
			//TODO log no detail product error
			return ERROR;
		}
		if (this.departDate.before(detailDO.getGmtStart()) || this.departDate.after(detailDO.getGmtEnd())) {
			//TODO log depart date out of the range error
			return ERROR;
		}
		OrderUtils.setSelfDriveOrderStartEndDate(orderDO, selfDriveDO.getDays());
		selfDriveDO.setDetails(Arrays.asList(detailDO));
		MemberDO memberDO = this.memberService.queryMember(orderDO.getMemberId());
		if (memberDO == null) {
			//TODO LOG can't find operated member error
			return ERROR;
		}
		memberDO.setPassword(null);
		memberDO.setMemberId(null);
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(orderDO.getGmtOrderStart());
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(orderDO.getGmtOrderEnd());
		this.getHttpSession().setAttribute("startDate", startDate);
		this.getHttpSession().setAttribute("endDate", endDate);
		this.getHttpSession().setAttribute("member", memberDO);
		getHttpSession().setAttribute("product", selfDriveDO);
		getHttpSession().setAttribute("order", orderDO);
		return SUCCESS;
	}
	
	public String makeHotelOrder() {
		
		return SUCCESS;
	}
	
	public String makeHotelRoomOrder() {
		
		return SUCCESS;
	}
	
	public String makeTicketOrder() {
		OrderDO orderDO = new OrderDO();
		orderDO.setProductType("4");
		orderDO.setProductId(this.productId);
		orderDO.setProductDetailId(this.productDetailId);
		
		TicketDO ticketDO = this.ticketService.getTicket(orderDO.getProductId());
		if(ticketDO == null) {
			//TODO log ticket product not exist error
			return ERROR;
		}
		ProductDetailDO detailDO = this.productDetailService.getProductDetail(this.productId, this.productDetailId);
		if (detailDO == null) {
			//TODO log ticket detail error;
			return ERROR;
		}
		ticketDO.setDetails(Arrays.asList(detailDO));
		this.getHttpSession().setAttribute("product", ticketDO);
		this.getHttpSession().setAttribute("order", orderDO);
		return SUCCESS;
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
	
	public void setAdultNum(String adultNum) {
		try {
			this.adultNum = Integer.valueOf(adultNum);
		} catch(Exception e) {
			this.adultNum = 0;
		}
	}

	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(String childNum) {
		try {
			this.childNum = Integer.valueOf(childNum);
		} catch(Exception e) {
			this.childNum = 0;
		}
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

	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	
	public Integer getTicketNum() {
		return ticketNum;
	}
	
	public void setTicketNum(Integer ticketNum) {
		this.ticketNum = ticketNum;
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

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
}