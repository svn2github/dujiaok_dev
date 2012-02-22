package com.ssnn.dujiaok.web.action.order;

import java.util.HashMap;
import java.util.Map;

import com.ssnn.dujiaok.biz.service.order.AlipayService;
import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.model.order.Order;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.web.action.BasicAction;


public class AlipayOrderAction extends BasicAction {
	private String orderId;
	
	private OrderService orderService;
	@Override
	public String execute() {
		if (orderId == null) {
            return ERROR;
        }

        OrderDO order = this.orderService.getOrderAndDetailContact(this.orderId);
        if (order == null) {
            return ERROR;
        }
//        String productId = order.getProductId();
//        Product product = null;//productService.getProductById(productId);
//        if (product == null) {
//            return ERROR;
//        }

        String subject = order.getName();
        Double amount = order.getPrice().doubleValue();

        String alipayResult = buildAlipayForm(subject, orderId, amount);
        this.getHttpSession().setAttribute("alipayResult", alipayResult);
        return SUCCESS;
	}
	
	private String buildAlipayForm(String subject, String orderId, Double amount) {
        AlipayService service = new AlipayService();

        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("subject", "（度假OK 订单 www.dujiaok.com）" + subject);
        sParaTemp.put("out_trade_no", orderId + "");
        sParaTemp.put("payment_type", "1");
//        sParaTemp.put("total_fee", amount + "");
        sParaTemp.put("total_fee", "0.01");

        String result = service.create_direct_pay_by_user(sParaTemp);
        return result;
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
}
