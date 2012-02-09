//package com.ssnn.dujiaok.biz.dal.ibatis;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//
//import com.ssnn.dujiaok.biz.dal.HotelRoomDetailDAO;
//import com.ssnn.dujiaok.model.HotelRoomDetailDO;
//import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;
//import com.ssnn.dujiaok.util.UniqueIDUtil;
//import com.ssnn.dujiaok.util.enums.ProductEnums;
//
//@ContextConfiguration(locations = { "classpath:bean/biz-common.xml",
//		"classpath:bean/biz-dao.xml", "classpath:bean/biz-datasource.xml", })
//public class IBatisHotelRoomDetailDAOTest extends AbstractBaseJUnit4Test{
//
//	@Autowired
//	private HotelRoomDetailDAO hotelRoomDetailDAO ;
//	
//	@Test
//	public void test_insert(){
//		HotelRoomDetailDO roomDetail = new HotelRoomDetailDO() ;
//		roomDetail.setRoomId(UniqueIDUtil.getUniqueID(ProductEnums.HOTEL_ROOM)) ;
//		roomDetail.setGmtEnd(new Date()) ;
//		roomDetail.setGmtStart(new Date()) ;
//		roomDetail.setPrice(new BigDecimal("44.44")) ;
//		//hotelRoomDetailDAO.insertRoomDetail(roomDetail) ;
//	}
//	
//	@Test
//	public void test_get(){
//		List list = hotelRoomDetailDAO.queryRoomDetails("FJ1201181551564992") ;
//		System.out.println(list.size());
//	}
//	
//	
//}
