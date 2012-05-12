package com.ssnn.dujiaok.web.action.admin;

import java.util.Map;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.page.condition.OrderCondition;
import com.ssnn.dujiaok.biz.service.MemberService;
import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.model.MemberDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author shenjia.caosj 2012-2-2
 *
 */
@SuppressWarnings("serial")
public class OrderAction extends BasicAction implements ModelDriven<Pagination>{

	private QueryResult<OrderDO> orders ;
	
	private Pagination pagination = new Pagination(1);
	
	private OrderCondition condition = new OrderCondition() ;
	
	private String orderId ;
	
	private OrderService orderService ;
	
	private MemberService memberService ;
	
	private OrderDO order ;
	
	private MemberDO member ;
	
	public String list() throws Exception {
		Map<String,Object> conditionMap = condition.toConditionMap() ; 
		orders = orderService.getOrders(conditionMap , pagination ) ;
		return SUCCESS ;
	}
 
	public String execute() throws Exception {
		order = orderService.getOrderAndDetailContact(orderId) ;
		if(order != null){
			member = memberService.queryMember(order.getMemberId()) ;
		}
		return SUCCESS ;
	}
	
	
	@Override
	public Pagination getModel() {
		return pagination ;
	}

	/*---------------------------------------------------------------------------------------------------------------------*/

	
	public QueryResult<OrderDO> getOrders() {
		return orders;
	}
	
	public OrderDO getOrder() {
		return order;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	
	public OrderCondition getCondition() {
		return condition;
	}

	public void setCondition(OrderCondition condition) {
		this.condition = condition;
	}

	public void setOrders(QueryResult<OrderDO> orders) {
		this.orders = orders;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public MemberDO getMember() {
		return member;
	}

	
	
	
}
