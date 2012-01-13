package com.ssnn.dujiaok.biz.service.order.impl;

import com.ssnn.dujiaok.biz.dal.order.OrderDao;
import com.ssnn.dujiaok.biz.service.order.OrderService;
import com.ssnn.dujiaok.model.order.Order;

public class OrderServiceImpl implements OrderService {
	private OrderDao orderDao;
	
	@Override
	public Integer addOrder(Order order) {
		return this.orderDao.addOrder(order);
	}
	
	@Override
	public Order getOrderById(Integer orderId) {
		return this.orderDao.getOrderById(orderId);
	}
	
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
}
