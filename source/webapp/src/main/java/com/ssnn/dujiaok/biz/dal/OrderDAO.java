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
 * @author langben 2012-2-7
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
	 * 统计
	 * @param condition
	 * @return
	 */
	int countOrders(Map<String,Object> condition) ;
	
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
	/**
	 * 
	 * @param orderId
	 * @param alipayId
	 * @param alipayStatus
	 * @return
	 */
	 int updateAlipayStatus(String orderId, String alipayId,
			 String payStatus, String orderStatus);
	 
	 /**
	  * 更新订单备注
	  * @param orderId
	  * @param memo
	  * @return
	  */
	 int updateMemoByOrderId(String orderId , String memo) ;
}
