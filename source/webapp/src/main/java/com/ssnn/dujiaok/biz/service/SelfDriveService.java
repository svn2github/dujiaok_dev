package com.ssnn.dujiaok.biz.service;

import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.model.SelfDriveDO;

/**
 * 自驾
 * @author langben 2012-1-19
 *
 */
public interface SelfDriveService {

	/**
	 * 获取SelfDrive,不包含detail
	 * @param selfDriveId
	 * @return
	 */
	SelfDriveDO getSelfDrive(String selfDriveId) ;
	
	/**
	 * 获取SelfDrive,包含detail
	 * @param selfDriveId
	 * @return
	 */
	SelfDriveDO getSelfDriveWithDetails(String selfDriveId) ;
	
	
	/**
	 * 创建SelfDrive,包含detail
	 * @param selfDriveId
	 */
	SelfDriveDO createSelfDriveAndDetails(SelfDriveDO selfDrive) ;
	
	
	/**
	 * 创建SelfDrive,包含Detail
	 * @param selfDriveId
	 */
	SelfDriveDO updateSelfDriveAndDetails(SelfDriveDO selfDrive) ;
	
	/**
	 * 查询
	 * @param condition
	 * @param pagination
	 * @return
	 */
	QueryResult<SelfDriveDO> getSelfDrives(Map<String,Object> condition , Pagination pagination) ;
	
	/**
	 * 
	 * @param selfDriveId
	 */
	void deleteSelfDrive(String selfDriveId) ;
}
