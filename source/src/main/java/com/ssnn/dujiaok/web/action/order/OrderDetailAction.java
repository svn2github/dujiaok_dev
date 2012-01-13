package com.ssnn.dujiaok.web.action.order;

import java.util.Date;

import com.ssnn.dujiaok.biz.service.order.OrderService;
import com.ssnn.dujiaok.biz.service.product.ProductService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.model.order.Order;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.ContextHolder;

public class OrderDetailAction extends BasicAction {
	private String tourId;
	private Integer productId;
	private Double amount;
	private String perosonInfo;
	private String comment;
	private String day;
	private String singlePrice;
	private String doublePrice;
	private String childPrice;
	private String countChild;
	private String adultNumber;
	private String isNeedRiskInput;
	private String comboNumber;
	private String endDay;
	private String startDay;
	private String Combo_Num;
	private String hotelPrice;
	
	private OrderService orderService;
	private ProductService productService;
	
	@Override
	public String execute() {
		Order order = new Order();
//        order.setUserId(new Integer(this.getLoginUser().getPersonId()));
        order.setProductId(productId);
        order.setTypeId(0);
        order.setPayStatus(Constant.ORDER_PAY_STATUS_2);
        order.setStatus(Constant.STATUS_1);
        order.setTotalPrice(amount);
        order.setPersonInfo(perosonInfo);
        order.setComment(comment);
        order.setStartDate(day);
        order.setUserId(2);
        
        if (null != singlePrice) {
//            order.setSingleNumber(new Integer(this.getRequest().getParameter("singleNumber")));
            order.setSinglePrice(new Double(singlePrice));
//            order.setDoubleNumber(new Integer(this.getRequest().getParameter("doubleNumber")));
            order.setDoublePrice(new Double(doublePrice));
            order.setChildNumber(new Integer(countChild));
            order.setChildPrice(new Double(childPrice));
            if ("y".equals(isNeedRiskInput)) {
                order.setInsure(new Integer(adultNumber));
            } else {
                order.setInsure(0);
            }
            order.setAdultNumber(new Integer(adultNumber));
        } else if (null != comboNumber) {
            order.setComboNumber(new Integer(comboNumber));
        } else {
            try{
                order.setHotelNumber(new Integer(Combo_Num));
                order.setHotelPrice(new Double(hotelPrice));//暂时只能存第一个，多了没地方存了
            }catch (Exception e) {
                return ERROR;
            }
            order.setEndDate(endDay);
            Date startDate = null;
            Date endDate = null;
//            try {
//                startDate = DateUtil.parseDate(order.getStartDate());
//                endDate = DateUtil.parseDate(order.getEndDate());
//            } catch (Exception e) {
//                
//            }
//            order.setHotelDay(DateUtil.compareDays(endDate, startDate));
        }
        int result =  this.orderService.addOrder(order);
        Product product = this.productService.getProductById(order.getProductId());
        this.getHttpSession().setAttribute("orderId", result);
        this.getHttpSession().setAttribute("order", order);
        this.getHttpSession().setAttribute("product", product);
		return SUCCESS;
	}

	public String getTourId() {
		return tourId;
	}

	public void setTourId(String tourId) {
		this.tourId = tourId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPerosonInfo() {
		return perosonInfo;
	}

	public void setPerosonInfo(String perosonInfo) {
		this.perosonInfo = perosonInfo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(String singlePrice) {
		this.singlePrice = singlePrice;
	}

	public String getDoublePrice() {
		return doublePrice;
	}

	public void setDoublePrice(String doublePrice) {
		this.doublePrice = doublePrice;
	}

	public String getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(String childPrice) {
		this.childPrice = childPrice;
	}

	public String getCountChild() {
		return countChild;
	}

	public void setCountChild(String countChild) {
		this.countChild = countChild;
	}

	public String getAdultNumber() {
		return adultNumber;
	}

	public void setAdultNumber(String adultNumber) {
		this.adultNumber = adultNumber;
	}

	public String getIsNeedRiskInput() {
		return isNeedRiskInput;
	}

	public void setIsNeedRiskInput(String isNeedRiskInput) {
		this.isNeedRiskInput = isNeedRiskInput;
	}

	public String getComboNumber() {
		return comboNumber;
	}

	public void setComboNumber(String comboNumber) {
		this.comboNumber = comboNumber;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getCombo_Num() {
		return Combo_Num;
	}

	public void setCombo_Num(String combo_Num) {
		Combo_Num = combo_Num;
	}

	public String getHotelPrice() {
		return hotelPrice;
	}

	public void setHotelPrice(String hotelPrice) {
		this.hotelPrice = hotelPrice;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
