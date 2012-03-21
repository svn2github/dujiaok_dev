package com.ssnn.dujiaok.biz.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.service.OrderService;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;

@ContextConfiguration(locations={
		"classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml",
		"classpath:bean/biz-datasource.xml" ,
		"classpath:bean/biz-service.xml" ,
	})
public class OrderServiceTest extends AbstractBaseJUnit4Test {
	@Resource
	private OrderService orderService;
	
	@Test
	public void testUpdateAlipayIdAndStatus() {
		String orderId = "16NF63CHRN2";
		
		orderService.updateAlipayStatus(orderId, "test", "trade_finished");
	}
}
