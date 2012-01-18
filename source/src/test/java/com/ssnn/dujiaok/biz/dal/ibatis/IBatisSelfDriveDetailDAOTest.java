package com.ssnn.dujiaok.biz.dal.ibatis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ssnn.dujiaok.biz.dal.SelfDriveDetailDAO;
import com.ssnn.dujiaok.model.SelfDriveDetailDO;
import com.ssnn.dujiaok.test.AbstractBaseJUnit4Test;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

@ContextConfiguration(locations = { "classpath:bean/biz-common.xml",
		"classpath:bean/biz-dao.xml", "classpath:bean/biz-datasource.xml", })
public class IBatisSelfDriveDetailDAOTest extends AbstractBaseJUnit4Test{

	@Autowired
	private SelfDriveDetailDAO selfDriveDetailDAO ;
	
	@Test
	public void test_insert(){
		
		SelfDriveDetailDO selfDriveDetail = new SelfDriveDetailDO() ;
		selfDriveDetail.setSelfDriveId(UniqueIDUtil.getUniqueID(ProductEnums.SELFDRIVE)) ;
		selfDriveDetail.setChildPrice(new BigDecimal("11.11")) ;
		selfDriveDetail.setDoublePrice(new BigDecimal("22.22")) ;
		selfDriveDetail.setGmtEnd(new Date()) ;
		selfDriveDetail.setGmtStart(new Date()) ;
		selfDriveDetail.setSinglePrice(new BigDecimal("33.33")) ;
		selfDriveDetailDAO.insertSelfDriveDetail(selfDriveDetail) ;
	}
	
	@Test
	public void test_get(){
		List l = selfDriveDetailDAO.querySelfDriveDetails("ZJ1201190007531083") ;
		System.out.println(l.size());
	}
	
	
}
