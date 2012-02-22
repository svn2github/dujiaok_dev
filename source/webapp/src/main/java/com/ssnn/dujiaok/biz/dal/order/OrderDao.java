package com.ssnn.dujiaok.biz.dal.order;

import com.ssnn.dujiaok.model.order.Order;

public interface OrderDao {
	/**
	 * add order
	 * @param order
	 * @return
	 */
	public Integer addOrder(Order order);
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	public Order getOrderById(Integer orderId);
	/**
	 * 
	 * @param orderId
	 * @param alipayId
	 * @param alipayStatus
	 * @return
	 */
	public int updateAlipayIdAndStatus(int orderId, String alipayId, String alipayStatus);
}
