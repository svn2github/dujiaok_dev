package com.ssnn.dujiaok.web.action.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.biz.service.alipay.AlipayService;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class AlipayOrderAction extends BasicAction {
	
	private String payBank ;
	
	private String orderId;
	
	private OrderService orderService;
	
	private AlipayService alipayService ;
	
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
        BigDecimal amount = order.getPrice();

        String alipayResult = buildAlipayForm(subject, orderId, amount , payBank);
        this.getHttpSession().setAttribute("alipayResult", alipayResult);
        return SUCCESS;
	}
	
	private String buildAlipayForm(String subject, String orderId, BigDecimal amount , String payBank) {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("subject", "（度假OK 订单 www.dujiaok.com）" + subject);
        sParaTemp.put("out_trade_no", orderId + "");
        sParaTemp.put("payment_type", "1");
        sParaTemp.put("total_fee", amount.toString());
        if(StringUtils.isBlank(payBank)){//支付宝支付
        	sParaTemp.put("paymethod", "directPay") ;
        }else{//网银支付
        	sParaTemp.put("paymethod", "bankPay") ;
        	sParaTemp.put("defaultbank", payBank) ;
        }
        String result = alipayService.create_direct_pay_by_user(sParaTemp , amount);
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

	public void setAlipayService(AlipayService alipayService) {
		this.alipayService = alipayService;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	
	
}
