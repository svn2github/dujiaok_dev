package com.ssnn.dujiaok.web.action.order;

import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class PayDetailAction extends BasicAction {
	private String orderId;
	private OrderService orderService;
	
	@Override
	public String execute() {
		OrderDO orderDO = this.orderService.getOrderAndDetailContact(this.orderId);
        if(orderDO == null){
            return ERROR;
        }
        this.getHttpSession().setAttribute("orderId", orderId);
        this.getHttpSession().setAttribute("order", orderDO);
        return SUCCESS;
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
}
