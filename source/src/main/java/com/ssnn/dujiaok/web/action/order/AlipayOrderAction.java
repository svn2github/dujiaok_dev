package com.ssnn.dujiaok.web.action.order;

import java.util.HashMap;
import java.util.Map;

import com.ssnn.dujiaok.biz.service.order.AlipayService;
import com.ssnn.dujiaok.biz.service.order.OrderService;
import com.ssnn.dujiaok.biz.service.product.ProductService;
import com.ssnn.dujiaok.model.order.Order;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.web.action.BasicAction;


public class AlipayOrderAction extends BasicAction {
	private String orderId;
	
	private OrderService orderService;
	private ProductService productService;
	@Override
	public String execute() {
		if (orderId == null) {
            return ERROR;
        }
        int orderId = new Integer(this.orderId);

        Order order = this.orderService.getOrderById(orderId);
        if (order == null) {
            return ERROR;
        }
        int productId = order.getProductId();
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ERROR;
        }

        String subject = product.getName();
        Double amount = order.getTotalPrice();

        String alipayResult = buildAlipayForm(subject, orderId, amount);
        this.getHttpSession().setAttribute("alipayResult", alipayResult);
        return SUCCESS;
	}
	
	private String buildAlipayForm(String subject, int orderId, Double amount) {
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

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
