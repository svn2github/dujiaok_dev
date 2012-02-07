package com.ssnn.dujiaok.web.action.admin;

import java.util.List;

import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author shenjia.caosj 2012-2-2
 *
 */
@SuppressWarnings("serial")
public class OrderAction extends BasicAction {

	private List<OrderDO> orders ;
	
	private String orderId ;
	
	public String list() throws Exception {
		
		return SUCCESS ;
	}

	public List<OrderDO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDO> orders) {
		this.orders = orders;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
