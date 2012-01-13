package com.ssnn.dujiaok.biz.dal.order.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.order.OrderDao;
import com.ssnn.dujiaok.model.order.Order;

public class OrderDaoImpl extends SqlMapClientDaoSupport implements OrderDao {

	@Override
	public Integer addOrder(Order order) {
		return (Integer) this.getSqlMapClientTemplate().insert("order.addOrder", order);
	}
	
	@Override
	public Order getOrderById(Integer orderId) {
		return (Order) this.getSqlMapClientTemplate().queryForObject("order.getOrderById", orderId);
	}
}
