package com.ssnn.dujiaok.biz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.model.OrderDO;

/**
 * 订单
 * @author shenjia.caosj 2012-2-2
 *
 */
public interface OrderService {

	/**
	 * 
	 * @param order
	 * @return
	 */
	OrderDO createOrderAndDetailContact(OrderDO order) ;
	 
	/**
	 * 
	 * @param status
	 * @param statusDetail
	 * @param orderId
	 */
	void updateOrderStatus(String status , String statusDetail ,String orderId) ;
	
	/**
	 * 
	 * @param payStatus
	 * @param gmtPaid
	 * @param orderId
	 */
	void updatePayStatus(String payStatus , Date gmtPaid ,String orderId) ;
	
	/**
	 * 
	 * @param alipayId
	 * @param orderId
	 */
	void updateAlipayStatus(String alipayId,String orderId) ;
	
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	OrderDO getOrderAndDetailContact(String orderId) ;
	
	/**
	 * 
	 * @param memberId
	 * @return
	 */
	List<OrderDO> getOrdersByMember(String memberId) ;
	
	/**
	 * 
	 * @param condition
	 * @param pagination
	 * @return
	 */
	QueryResult<OrderDO> getOrders(Map<String,Object> condition , Pagination pagination) ;
}
