package com.ssnn.dujiaok.biz.dal.ibatis;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.dal.OrderDAO;
import com.ssnn.dujiaok.model.OrderContactDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.OrderStatusEnums;
import com.ssnn.dujiaok.util.enums.PayStatusEnums;
import com.ssnn.dujiaok.util.enums.PayTypeEnums;
import com.ssnn.dujiaok.util.enums.ProductEnums;

@ContextConfiguration(locations = { "classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml", "classpath:bean/biz-datasource.xml", })
public class IBatisOrderDAOTest extends AbstractBaseJUnit4Test{

	@Autowired
	private OrderDAO orderDAO ;
	
	@Test
	public void test_insertOrder(){
		OrderDO order = new OrderDO () ;
		order.setCount(10) ;
		order.setGmtOrderEnd(new Date()) ;
		order.setGmtOrderStart(new Date()) ;
		order.setGmtPaid(new Date()) ;
		order.setMemberId("testfree") ;
		order.setMemo("memo") ;
		order.setName("越南-缅甸10日游-订单") ;
		order.setOrderId(UniqueIDUtil.getOrderID("testfree")) ;
		order.setPayStatus(PayStatusEnums.PAID.getName()) ;
		order.setPayType(PayTypeEnums.CASH.getName()) ;
		order.setPrice(new BigDecimal("21.44")) ;
		order.setProductId(UniqueIDUtil.getUniqueID(ProductEnums.HOTEL)) ;
		order.setProductType(ProductEnums.HOTEL.getName()) ;
		order.setSecondaryCount(100) ;
		order.setStatus(OrderStatusEnums.CLOSED.getName()) ;
		order.setAlipayId("ALIPAY_ID") ;
		order.setStatusDetail("超时未付款");
		orderDAO.insertOrder(order) ;
	}
	
	@Test
	public void test_insertContact(){
		OrderContactDO contact = new OrderContactDO() ;
		contact.setCertificateNumber("33048221213233333") ;
		contact.setCertificateType("身份证") ;
		contact.setEmail("bigdjw@173.com") ;
		//contact.setIsMain("T") ;
		contact.setMobile("135454546466") ;
		contact.setName("克劳才") ;
		contact.setOrderId("120208144156870791146240514") ;
		orderDAO.insertOrderContact(contact) ;
	}
}
