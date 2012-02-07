package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.OrderDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.OrderContactDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.OrderDetailDO;

public class IBatisOrderDAO extends SqlMapClientDaoSupport implements OrderDAO {

	@Override
	public void insertOrder(OrderDO order) {
		getSqlMapClientTemplate().insert("order.insertOrder" , order) ;
	}

	@Override
	public void updateOrder(OrderDO order) {
		getSqlMapClientTemplate().update("order.updateOrder" , order) ;
	}

	@Override
	public void updateOrderStatus(String status, String statusDetail,String orderId) {
		Map<String,Object> m = new HashMap<String,Object>() ;
		m.put("status" , status) ;
		m.put("statusDetail", statusDetail) ;
		m.put("orderId" ,orderId) ;
		getSqlMapClientTemplate().update("order.updateOrderStatus" , m) ;
	}

	@Override
	public void updatePayStatus(String payStatus, Date gmtPaid,String orderId) {
		Map<String,Object> m = new HashMap<String,Object>() ;
		m.put("payStatus" , payStatus) ;
		m.put("gmtPaid", gmtPaid) ;
		m.put("orderId" ,orderId) ;
		getSqlMapClientTemplate().update("order.updatePayStatus" , m) ;
	}

	@Override
	public void updateAlipayStatus(String alipayId,String orderId) {
		Map<String,Object> m = new HashMap<String,Object>() ;
		m.put("alipayId" , alipayId) ;
		m.put("orderId" ,orderId) ;
		getSqlMapClientTemplate().update("order.updateAlipayStatus" , m) ;
	}

	@Override
	public OrderDO queryOrder(String orderId) {
		return (OrderDO)getSqlMapClientTemplate().queryForObject("order.queryOrder" , orderId) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> queryOrdersByMember(String memberId) {
		return getSqlMapClientTemplate().queryForList("order.queryOrdersByMember" , memberId) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> queryOrders(Map<String, Object> condition, Pagination pagination) {
		condition.put("start", pagination.getStart()-1) ;
		condition.put("size", pagination.getSize()) ;
		return getSqlMapClientTemplate().queryForList("order.queryOrders" , condition) ;
	}

	@Override
	public void insertOrderContact(OrderContactDO contact) {
		getSqlMapClientTemplate().insert("order.insertOrderContact" , contact) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderContactDO> queryOrderContactsByOrder(String orderId) {
		return getSqlMapClientTemplate().queryForList("order.queryOrderContactsByOrder" , orderId) ;
	}

	@Override
	public void insertOrderDetail(OrderDetailDO detail) {
		getSqlMapClientTemplate().insert("order.insertOrderDetail" , detail) ;
	}

	@Override
	public OrderDetailDO queryOrderDetailByOrder(String orderId) {
		return (OrderDetailDO)getSqlMapClientTemplate().queryForObject("order.queryOrderDetailByOrder" , orderId) ;
	}

}
