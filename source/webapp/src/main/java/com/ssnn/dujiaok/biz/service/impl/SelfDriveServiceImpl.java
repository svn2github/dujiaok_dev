package com.ssnn.dujiaok.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.dal.ProductDetailDAO;
import com.ssnn.dujiaok.biz.dal.SelfDriveDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

/**
 * 
 * @author shenjia.caosj 2012-1-19
 *
 */
public class SelfDriveServiceImpl implements SelfDriveService{

	private SelfDriveDAO selfDriveDAO ;
	
	private ProductDetailDAO productDetailDAO ;
	
	public void setSelfDriveDAO(SelfDriveDAO selfDriveDAO) {
		this.selfDriveDAO = selfDriveDAO;
	}
	
	public void setProductDetailDAO(ProductDetailDAO productDetailDAO) {
		this.productDetailDAO = productDetailDAO;
	}


	@Override
	public SelfDriveDO getSelfDrive(String selfDriveId) {
		return selfDriveDAO.querySelfDrive(selfDriveId) ;
	}

	@Override
	public SelfDriveDO getSelfDriveWithDetails(String selfDriveId) {
		SelfDriveDO selfDrive = selfDriveDAO.querySelfDrive(selfDriveId) ;
		if(selfDrive != null){
			List<ProductDetailDO> details = productDetailDAO.queryDetails(selfDriveId) ;
			selfDrive.setDetails(details) ;
		}
		return selfDrive ;
	}

	@Override
	public SelfDriveDO createSelfDriveAndDetails(SelfDriveDO selfDrive) {
		List<ProductDetailDO> details = selfDrive.getDetails() ;
		if(details!=null){
			Date gmtExpire = getExpireDate(details) ;
			selfDrive.setGmtExpire(gmtExpire) ;
			selfDrive.setProductId(UniqueIDUtil.buildUniqueId(ProductEnums.SELFDRIVE)) ;
			selfDriveDAO.insertSelfDrive(selfDrive) ;
			for(ProductDetailDO detail : details){
				detail.setProductId(selfDrive.getProductId()) ;
				productDetailDAO.insertDetail(detail) ;
			}
		}
		return selfDrive ;
	}

	@Override
	public SelfDriveDO updateSelfDriveAndDetails(SelfDriveDO selfDrive) {
		List<ProductDetailDO> details = selfDrive.getDetails() ;
		if(details!=null){
			//删除之前的detail 重新插入
			productDetailDAO.deleteDetails(selfDrive.getProductId()) ;
			Date gmtExpire = getExpireDate(details) ;
			selfDrive.setGmtExpire(gmtExpire) ;
			selfDriveDAO.updateSelfDrive(selfDrive) ;
			for(ProductDetailDO detail : details){
				detail.setProductId(selfDrive.getProductId()) ;
				productDetailDAO.insertDetail(detail) ;
			}
		}
		return selfDrive ;
	}
	
	
	private Date getExpireDate(List<ProductDetailDO> details){
		Date gmtExpire = null ;
		for(ProductDetailDO detail : details){
			Date gmtEnd = detail.getGmtEnd() ;
			if(gmtExpire == null){
				gmtExpire = gmtEnd ;
			}else{
				if(gmtExpire.before(gmtEnd)){
					gmtExpire = gmtEnd ;
				}
			}
		}
		return gmtExpire ;
	}

	@Override
	public QueryResult<SelfDriveDO> getSelfDrives(Map<String, Object> condition,Pagination pagination) {
		pagination.setTotalCount(selfDriveDAO.countSelfDrives(condition)) ;
		List<SelfDriveDO> items = selfDriveDAO.querySelfDrives(condition, pagination) ;
		QueryResult<SelfDriveDO> result = new QueryResult<SelfDriveDO>(items,pagination) ;
		return result ;
	}

	@Override
	public void deleteSelfDrive(String selfDriveId) {
		selfDriveDAO.deleteSelfDrive(selfDriveId) ;
		productDetailDAO.deleteDetails(selfDriveId) ;
	}

}
