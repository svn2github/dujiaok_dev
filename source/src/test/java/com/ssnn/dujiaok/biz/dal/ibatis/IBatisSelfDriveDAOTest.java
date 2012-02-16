package com.ssnn.dujiaok.biz.dal.ibatis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.dal.SelfDriveDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

@ContextConfiguration(locations = { "classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml", "classpath:bean/biz-datasource.xml", })
public class IBatisSelfDriveDAOTest extends AbstractBaseJUnit4Test {

	@Autowired
	private SelfDriveDAO selfDriveDAO ;
	
	@Test
	public void test_insert(){
		SelfDriveDO selfDrive = new SelfDriveDO() ;
		selfDrive.setDays(10) ;
		selfDrive.setDestAddr("网商路600好") ;
		selfDrive.setDestArea("滨江");
		selfDrive.setDestCity("杭州");
		selfDrive.setDestProvince("浙江");
		selfDrive.setFeeDesc("fee desv") ;
		selfDrive.setGmtExpire(new Date()) ;
		selfDrive.setIntroduction("intro") ;
		selfDrive.setImages("images") ;
		selfDrive.setProductTypes("pro types") ;
		selfDrive.setLocationCode("loc code") ;
		selfDrive.setMarketPrice(new BigDecimal("22.66")) ;
		selfDrive.setMemo("memo") ;
		selfDrive.setName("自驾001") ;
		selfDrive.setPayTypes("paytypes") ;
		selfDrive.setSchedule("schedule") ;
		selfDrive.setProductId(UniqueIDUtil.buildUniqueId(ProductEnums.SELFDRIVE)) ;
		selfDriveDAO.insertSelfDrive(selfDrive) ;
	}
	
	@Test
	public void test_query(){
		SelfDriveDO selfDrive  = selfDriveDAO.querySelfDrive("ZJ1201182354069713") ;
		int a = 5;
	}
	
	@Test
	public void test_querys(){
		
		Map condition = new HashMap() ;
		List<SelfDriveDO> s = selfDriveDAO.querySelfDrives(condition, new Pagination(1)) ;
		System.out.println(s.size());
	}
	
	
}
