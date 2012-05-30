package com.ssnn.dujiaok.web.action.order;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.biz.service.ProductService;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.util.enums.PayTypeEnums;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class PayDetailAction extends BasicAction {
	private String orderId;
	private OrderService orderService;
	private OrderDO order ;
	
	private ProductService productService ;
		
	@Override
	public String execute() {
		OrderDO orderDO = this.orderService.getOrderAndDetailContact(this.orderId);
        if(orderDO == null){
            return ERROR;
        }
        order = orderDO ;
        String payType = order.getPayType() ;
        if(StringUtils.equals(payType, PayTypeEnums.ONLINE.getName())){
        	return "online" ;
        }else if(StringUtils.equals(payType, PayTypeEnums.CASH.getName())){
        	return "cash" ;
        }
        return "online" ;
        
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrderDO getOrder() {
		return order;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	
}
