package com.ssnn.dujiaok.biz.dal.ibatis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.dal.TicketDAO;
import com.ssnn.dujiaok.biz.dal.TicketDetailDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.TicketDetailDO;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;

@ContextConfiguration(locations = { "classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml", "classpath:bean/biz-datasource.xml", })
public class IBatisTicketDAOTest extends AbstractBaseJUnit4Test {

	@Autowired
	private TicketDAO ticketDAO;

	@Autowired
	private TicketDetailDAO ticketDetailDAO;

	@Test
	public void test_createTicket() {
		TicketDO ticket = new TicketDO();
		ticket.setDestAddr("网商路699号");
		ticket.setDestArea("滨江区");
		ticket.setDestProvince("浙江省");
		ticket.setDestCity("杭州市");
		ticket.setFeeDesc("FEE DESC");
		ticket.setImages("images");
		ticket.setIntroduction("introductions");
		ticket.setLocationCode("locationcode");
		ticket.setMarketPrice(new BigDecimal("12.44"));
		ticket.setMemo("memo");
		ticket.setName("厦门三日游");
		ticket.setNotice("notice");
		ticket.setPayTypes("zhifubao,taobao,wangyin");
		ticket.setProductTypes("景区,是我的");
		// ticket.setTicketDetails(ticketDetails)
		ticketDAO.insertTicket(ticket);
	
	}

	@Test
	public void test_getTicket() {
		TicketDO ticket = ticketDAO.queryTicket(2);
		String notice = ticket.getNotice();
	}

	@Test
	public void test_createTicketDetail() {
		TicketDetailDO ticketDetail = new TicketDetailDO();
		ticketDetail.setGmtEnd(new Date());
		ticketDetail.setGmtStart(new Date());
		ticketDetail.setPrice(new BigDecimal("22.33"));
		ticketDetail.setTicketId(234656);
		ticketDetailDAO.insertTicketDetail(ticketDetail);
	}

	@Test
	public void test_getTicketDetails() {
		List<TicketDetailDO> ticketDetails = ticketDetailDAO
				.queryTicketDetail(2);
		int size = ticketDetails.size();
	}

	@Test
	public void test_deleteTicketDetails() {
		ticketDetailDAO.deleteTicketDetails(2);
	}
	
	@Test
	public void test_getTickets(){
		List<TicketDO> tickets = ticketDAO.queryTickets(new HashMap<String,Object>(), new Pagination(20));
		int a = 5 ;
	}

}
