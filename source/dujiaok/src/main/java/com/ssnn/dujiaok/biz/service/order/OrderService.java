package com.ssnn.dujiaok.biz.service.order;

import com.ssnn.dujiaok.model.order.Order;

public interface OrderService {
	public Integer addOrder(Order order);
	
	public Order getOrderById(Integer orderId);
	
	/**
	 * 根据订购单id更新支付宝交易号和交易状态
	 * @param out_trade_no
	 * @param trade_no
	 * @param trade_status
	 */
    public int updateAlipayIdAndStatus(Integer orderId, String alipayId, String alipayStatus);
}
