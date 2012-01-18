package com.ssnn.dujiaok.biz.dal.ibatis;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.dal.HotelRoomDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

@ContextConfiguration(locations = { "classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml", "classpath:bean/biz-datasource.xml", })
public class IBatisHotelRoomDAOTest extends AbstractBaseJUnit4Test {

	@Autowired
	private HotelRoomDAO hotelRoomDAO ;
	
	@Test
	public void test_insert(){
		HotelRoomDO room = new HotelRoomDO() ;
		room.setBed("1.5大床");
		room.setHotelId(UniqueIDUtil.getUniqueID(ProductEnums.HOTEL)) ;
		room.setMarketPrice(new BigDecimal("22.2")) ;
		room.setName("大床房");
		room.setPayTypes("支付宝");
		room.setRoomArea("24") ;
		room.setImages("imsgae") ;
		room.setIntroduction("intro") ;
		room.setMemo("memo") ;
		room.setRoomFacilities("配套");
		room.setRoomId(UniqueIDUtil.getUniqueID(ProductEnums.HOTEL_ROOM)) ;
		hotelRoomDAO.insertRoom(room) ;
	}
	
	@Test
	public void test_query(){
		HotelRoomDO room = hotelRoomDAO.queryRoom("FJ1201181518545586") ;
		String bed = room.getBed() ;
	}
	
	@Test
	public void test_querys(){
		Map<String,Object> condition = new HashMap<String,Object>() ;
		condition.put("hotelId", "JD1201181518545558") ;
		condition.put("roomId", "FJ1201181530466890") ;
		
		List<HotelRoomDO> rooms = hotelRoomDAO.queryRooms(condition,new Pagination(1)) ;
		int size = rooms.size() ;
	}
	
	@Test
	public void test_update(){
		
	}
}
