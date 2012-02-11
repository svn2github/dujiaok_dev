package com.ssnn.dujiaok.web.action.order;

import java.util.Date;

import ognl.enhance.OrderedReturn;

import com.ssnn.dujiaok.biz.service.product.ProductService;
import com.ssnn.dujiaok.biz.service.tour.TourService;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.OrderDetailDO;
import com.ssnn.dujiaok.model.Tour;
import com.ssnn.dujiaok.model.product.Product;
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
	private Date tourDate;
	/**
	 * 保险.
	 */
	private Integer insureNum;
	
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
	
	private TourService tourService;
	private ProductService productService;
	
	@Override
	public String execute() throws Exception  {
        int countTotal = this.adultNum + this.childNum;

        Tour tour = tourService.getTourById(new Integer(productDetailId));

        if (tour == null || tour.getStartDate() == null) {
            return SUCCESS;
        }

        if (tour.getChildPrice() == null) {
        	getHttpSession().setAttribute("childPrice", 0);
        } else {
        	getHttpSession().setAttribute("childPrice", tour.getChildPrice());
        }

        if (tour.getDoublePrice() == null) {
        	getHttpSession().setAttribute("doublePrice", 0);
        } else {
        	getHttpSession().setAttribute("doublePrice", tour.getDoublePrice());
        }

        if (tour.getSinglePrice() == null) {
        	getHttpSession().setAttribute("singlePrice", 0);
        } else {
        	getHttpSession().setAttribute("singlePrice", tour.getSinglePrice());
        }
        
        if (tour.getComboPrice() == null) {
            getHttpSession().setAttribute("comboPrice", 0);
        } else {
        	getHttpSession().setAttribute("comboPrice", tour.getComboPrice());
        }
        
        Product product = productService.getProductById(new Integer(productId));
        if(product != null){
        	getHttpSession().setAttribute("product", product);
        }

        getHttpSession().setAttribute("day", tour.getStartDate());
        getHttpSession().setAttribute("dest", product.getDest());
        getHttpSession().setAttribute("countPerson", this.adultNum);
        getHttpSession().setAttribute("countChild", this.childNum);
        getHttpSession().setAttribute("countTotal", countTotal);
        getHttpSession().setAttribute("productId", productId);
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
		orderDO.setMemberId(null);
		orderDO.setCount(this.adultNum);
		orderDO.setSecondaryCount(this.childNum);
		orderDO.setGmtOrderStart(this.tourDate);
		
		return SUCCESS;
	}
	
	public String makeHotelOrder() {
		
		return SUCCESS;
	}
	
	public String makeHotelRoomOrder() {
		
		return SUCCESS;
	}
	
	public String makeTicketOrder() {
		
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

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}

	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}

	public Date getTourDate() {
		return tourDate;
	}

	public void setTourDate(Date tourDate) {
		this.tourDate = tourDate;
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

	public TourService getTourService() {
		return tourService;
	}
	public void setTourService(TourService tourService) {
		this.tourService = tourService;
	}
	public ProductService getProductService() {
		return productService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
