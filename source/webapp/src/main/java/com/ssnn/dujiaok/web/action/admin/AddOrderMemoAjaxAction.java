package com.ssnn.dujiaok.web.action.admin;

import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.web.action.BasicAction;

public class AddOrderMemoAjaxAction extends BasicAction {

	private boolean success = false ;
	
	private String orderId ;
	
	private String memo ;
	
	private OrderService orderService ;
	
	@Override
	public String execute() throws Exception {
		
		if(StringUtils.isBlank(orderId)){
			return SUCCESS ;
		}
		
		int effectCount = orderService.updateMemoByOrderId(orderId, URLDecoder.decode(memo,"UTF-8")) ;
		success = true ;
		
		return SUCCESS ;
	}

	
	public boolean isSuccess() {
		return success;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	
}
