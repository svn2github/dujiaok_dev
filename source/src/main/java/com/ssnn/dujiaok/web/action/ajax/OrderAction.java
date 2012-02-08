package com.ssnn.dujiaok.web.action.ajax;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class OrderAction extends BasicAction {
	
	private Log logger = LogFactory.getLog(OrderAction.class) ;

	private String result ;
	
	private String status ;
	
	private String statusDetail ;
	
	private String orderId ;
	
	private OrderService orderService ;
	
	public String status() throws Exception{
		try{
			orderService.updateOrderStatus(status, statusDetail, orderId) ;
			result = SUCCESS ;
		}catch(Exception e){
			logger.error(e.getMessage() , e) ;
			result = ERROR ;
		}
		return SUCCESS ;
	}
	
	/*----------------------------------------------------------------------------------------------------**/

	public String getResult() {
		return result;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	

}
