package com.ssnn.dujiaok.web.action.order;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.biz.service.order.util.AlipayNotify;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class AlipayCallBackAction extends BasicAction {
	private String is_success;
	private String out_trade_no;
	private String subject;
	private String trade_no;
	private String trade_status;
	private String buyer_email;
	private String total_fee;
	
	private OrderService orderService;
	
	@Override
	public String execute() {
		Map<String,String> params = new HashMap<String,String>();
		Map<String, Object> requestParams = getParameters();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			try {
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			params.put(name, valueStr);
		}
		if (AlipayNotify.verify(params)) {
//            logger.info("alipayReturn ::: out_trade_no=" + out_trade_no + ", trade_no=" + trade_no + ", trade_status="
//                        + trade_status + "pensonId=" + this.getLoginUser().getPersonId());
            orderService.updateAlipayStatus(out_trade_no, trade_no, trade_status);
        } else {
        	//TODO log
        }
		
		return SUCCESS;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
}
