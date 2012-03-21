package com.ssnn.dujiaok.web.action.member;

import java.util.List;

import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.util.string.StringUtil;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.context.SessionUtil;

@SuppressWarnings("serial")
public class OrderListAction extends BasicAction {
	private List<OrderDO> orderList;
	private OrderService orderService;
	
	@Override
	public String execute() {
		MemberDO memberDO = SessionUtil.getLoginMemberDO(getHttpSession());
		if (memberDO == null || StringUtil.isEmpty(memberDO.getMemberId())) {
			getHttpSession().setAttribute("message", "无法获取用户信息，请重新登录。");
			return ERROR;
		}
		try {
			orderList = orderService.getOrdersByMember(memberDO.getMemberId());
		} catch (Exception e) {
			getHttpSession().setAttribute("message", "获取用户订单列表失败。");
			return ERROR;
		}
		memberDO.setPassword(null);
		getHttpSession().setAttribute("orderList", orderList);
		getHttpSession().setAttribute("member", memberDO);
		return SUCCESS;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public List<OrderDO> getOrderList() {
		return this.orderList;
	}
}
