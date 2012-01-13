package com.ssnn.dujiaok.biz.dal.order.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.order.OrderDao;
import com.ssnn.dujiaok.constant.Constant;
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
	
	@Override
	public int updateAlipayIdAndStatus(int orderId, String alipayId, String alipayStatus) {
	    Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("id", orderId);
        condition.put("alipayId", alipayId);
        condition.put("alipayStatus", alipayStatus);
        if (alipayStatus.equals(Constant.ALIPAY_INIT)) {
            condition.put("payStatus", Constant.ORDER_PAY_STATUS_2);
        } else if (alipayStatus.equals(Constant.ALIPAY_SUCCESS)) {
            condition.put("payStatus", Constant.ORDER_PAY_STATUS_1);
            condition.put("status", Constant.STATUS_2);
        } else {
            Order order = getOrderById(orderId);
            condition.put("payStatus", order.getPayStatus());
        }
        
        return (Integer)getSqlMapClientTemplate().update("order.updateAlipayIdAndStatus", condition);
	}
}
