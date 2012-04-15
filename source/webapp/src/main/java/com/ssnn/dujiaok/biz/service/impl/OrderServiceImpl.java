package com.ssnn.dujiaok.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.ssnn.dujiaok.biz.dal.MemberDAO;
import com.ssnn.dujiaok.biz.dal.OrderDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.model.OrderContactDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.AlipayStatusEnums;
import com.ssnn.dujiaok.util.enums.OrderStatusEnums;
import com.ssnn.dujiaok.util.enums.PayStatusEnums;

public class OrderServiceImpl implements OrderService {

	private OrderDAO orderDAO ;
	
	private MemberDAO memberDAO  ;
	
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	@Override
	public OrderDO insertOrderAndDetailContact(OrderDO order) {
//		if(order.getDetail() == null){
//			throw new IllegalArgumentException("detail cant be null") ;
//		}
		MemberDO member = memberDAO.queryMember(order.getMemberId()) ;
		if(member == null){
			return null ;
		}
		String orderId = UniqueIDUtil.buildOrderId(member) ;
		order.setOrderId(orderId) ;
		orderDAO.insertOrder(order) ;
//		OrderDetailDO detail = order.getDetail() ; 
//		detail.setOrderId(orderId) ;
//		orderDAO.insertOrderDetail(detail) ;
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
	public OrderDO getOrder(String orderId) {
		return this.orderDAO.queryOrder(orderId);
	}

	@Override
	public OrderDO getOrderAndDetailContact(String orderId) {
		OrderDO order = orderDAO.queryOrder(orderId) ;
		if(order != null){
			List<OrderContactDO> contacts = orderDAO.queryOrderContactsByOrder(orderId) ; 
			order.setContacts(contacts) ;
//			OrderDetailDO detail = orderDAO.queryOrderDetailByOrder(orderId) ; 
//			order.setDetail(detail) ;
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
	
	@Override
	public int updateAlipayStatus(String orderId, String alipayId, String alipayStatus) {
		PayStatusEnums payStatus = AlipayStatusEnums.toPayStatus(alipayStatus) ;
		if(payStatus == PayStatusEnums.PAID){
			return orderDAO.updateAlipayStatus(orderId, alipayId, payStatus.getName() , OrderStatusEnums.SUCCESS.getName());
		}else{
			return orderDAO.updateAlipayStatus(orderId, alipayId, payStatus.getName() , OrderStatusEnums.CONFIRM.getName());
		}
    }
}
