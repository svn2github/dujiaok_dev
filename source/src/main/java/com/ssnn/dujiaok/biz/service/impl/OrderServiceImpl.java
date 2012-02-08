package com.ssnn.dujiaok.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.ssnn.dujiaok.biz.dal.OrderDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.model.OrderContactDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.OrderDetailDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;

public class OrderServiceImpl implements OrderService {

	private OrderDAO orderDAO ;
	
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public OrderDO createOrderAndDetailContact(OrderDO order) {
		if(order.getDetail() == null){
			throw new IllegalArgumentException("detail cant be null") ;
		}
		String orderId = UniqueIDUtil.getOrderID(order.getMemberId()) ;
		order.setOrderId(orderId) ;
		orderDAO.insertOrder(order) ;
		OrderDetailDO detail = order.getDetail() ; 
		detail.setOrderId(orderId) ;
		orderDAO.insertOrderDetail(detail) ;
		if(!CollectionUtils.isEmpty(order.getContacts())){
			for(OrderContactDO contact : order.getContacts()){
				contact.setOrderId(orderId) ;
				orderDAO.insertOrderContact(contact) ;
			}
		}
		return order ;
	}

	@Override
	public void updateOrderStatus(String status, String statusDetail, String orderId) {
		orderDAO.updateOrderStatus(status, statusDetail, orderId) ;
	}

	@Override
	public void updatePayStatus(String payStatus, Date gmtPaid, String orderId) {
		orderDAO.updatePayStatus(payStatus, gmtPaid, orderId) ;
	}

	@Override
	public void updateAlipayStatus(String alipayId, String orderId) {
		orderDAO.updateAlipayStatus(alipayId, orderId) ;
	}

	@Override
	public OrderDO getOrderAndDetailContact(String orderId) {
		OrderDO order = orderDAO.queryOrder(orderId) ;
		if(order != null){
			List<OrderContactDO> contacts = orderDAO.queryOrderContactsByOrder(orderId) ; 
			order.setContacts(contacts) ;
			OrderDetailDO detail = orderDAO.queryOrderDetailByOrder(orderId) ; 
			order.setDetail(detail) ;
		}
		return order ;
	}

	@Override
	public List<OrderDO> getOrdersByMember(String memberId) {
		return orderDAO.queryOrdersByMember(memberId) ;
	}

	@Override
	public QueryResult<OrderDO> getOrders(Map<String, Object> condition,Pagination pagination) {
		int totalCount = orderDAO.countOrders(condition) ;
		pagination.setTotalCount(totalCount) ;	
		List<OrderDO> items = orderDAO.queryOrders(condition, pagination) ;
		return new QueryResult<OrderDO>(items, pagination) ;
	}

}
