package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.dal.HotelDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

@ContextConfiguration(locations = { "classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml", "classpath:bean/biz-datasource.xml", })
public class IBatisHotelDAOTest extends AbstractBaseJUnit4Test {

	@Autowired
	private HotelDAO hotelDAO;

	

	@Test
	public void test_create() {
		
		
		for(int i=0 ;i<40;i++){
			HotelDO hotel = new HotelDO () ;
			hotel.setProductId(UniqueIDUtil.buildUniqueId(ProductEnums.HOTEL)) ;
			hotel.setDestAddr("度假搜索测试00" + i) ;
			hotel.setDestArea("滨江");
			hotel.setDestCity("杭州");
			hotel.setDestProvince("浙江") ;
			hotel.setImages("images") ;
			hotel.setIntroduction("intro") ;
			hotel.setLocationCode("lc") ;
			hotel.setMemo("memo") ;
			hotel.setName("度假搜索酒店测试00" + i);
			hotel.setRoomAmount(20) ;
			hotel.setStarRate(10) ;
			
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 10) ;
			hotel.setGmtExpire(c.getTime()) ;
			System.out.println(hotel);
			hotelDAO.insertHotel(hotel) ;
			try {
				Thread.sleep(1000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

	@Test
	public void test_get() {
		HotelDO hotel = hotelDAO.queryHotel("JD1201171609535427") ;
		String a = hotel.getDestAddr() ;
//		TicketDO ticket = ticketDAO.queryTicket("2");
//		String notice = ticket.getNotice();
	}


	@Test
	public void test_getHotels(){
		Map<String,Object> condition = new HashMap<String,Object>() ;
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.DATE, -1) ;
//		condition.put("gmtExpire", c.getTime()) ;
//		condition.put("destProvince", "浙江省") ;
//		condition.put("destCity", "杭州市") ;
//		condition.put("name", "三日") ;
		List<HotelDO> hotels = hotelDAO.queryHotel(condition , new Pagination(0));
		int a = 5 ;
	}

}
