package com.ssnn.dujiaok.biz.dal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.OrderContactDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.OrderDetailDO;

/**
 * Order
 * @author shenjia.caosj 2012-2-7
 *
 */
public interface OrderDAO {

	/**
	 * 插入ORDER
	 * @param order
	 */
	void insertOrder(OrderDO order) ;
	
	/**
	 * 更新
	 * @param order
	 */
	void updateOrder(OrderDO order) ;
	
	/**
	 * 更新订单状态
	 * @param status
	 * @param statusDetail
	 */
	void updateOrderStatus(String status , String statusDetail ,String orderId) ;
	
	/**
	 * 更新付款状态
	 * @param payStatus
	 * @param gmtPaid
	 */
	void updatePayStatus(String payStatus , Date gmtPaid ,String orderId) ;
	
	/**
	 * 更新支付宝状态
	 * @param alipayId
	 */
	void updateAlipayStatus(String alipayId,String orderId) ;
	/**
	 * 查询订单
	 * @param orderId
	 * @return
	 */
	OrderDO queryOrder(String orderId) ;
	
	/**
	 * 查订单
	 * @param memberId
	 * @return
	 */
	List<OrderDO> queryOrdersByMember(String memberId) ;
	
	/**
	 * 查订单
	 * @param condition
	 * @param pagination
	 * @return
	 */
	List<OrderDO> queryOrders(Map<String,Object> condition , Pagination pagination) ;
	
	/**
	 * 订单联系人
	 * @param contact
	 */
	void insertOrderContact(OrderContactDO contact) ;
	
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	List<OrderContactDO> queryOrderContactsByOrder(String orderId) ;
	
	/**
	 * 
	 * @param detail
	 */
	void insertOrderDetail(OrderDetailDO detail) ;
	
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	OrderDetailDO queryOrderDetailByOrder(String orderId) ;
	
}
