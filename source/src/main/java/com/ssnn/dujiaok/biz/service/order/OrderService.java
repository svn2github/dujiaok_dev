package com.ssnn.dujiaok.biz.service.order;

import com.ssnn.dujiaok.model.order.Order;

public interface OrderService {
	public Integer addOrder(Order order);
	
	public Order getOrderById(Integer orderId);
}
