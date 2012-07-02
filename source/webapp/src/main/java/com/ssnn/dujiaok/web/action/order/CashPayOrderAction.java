package com.ssnn.dujiaok.web.action.order;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.util.enums.OrderStatusEnums;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.ContextHolder;

public class CashPayOrderAction extends BasicAction {

	private OrderService orderService;
	
	private String orderId ;
	
	@Override
	public String execute() throws Exception {
		String memberId = ContextHolder.getMemberContext().getMemberId() ;
		
		OrderDO order = orderService.getOrder(orderId) ;
		
		if(order == null || !StringUtils.equals(order.getMemberId() , memberId)){
			return SUCCESS ;
		}
		
		orderService.updateOrderStatus(OrderStatusEnums.CONFIRM.getName()	, null , orderId) ;
		
		return SUCCESS ;
	}
	
	

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	
}
