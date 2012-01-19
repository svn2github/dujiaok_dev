package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.model.SelfDriveDO;

/**
 * 自驾
 * @author shenjia.caosj 2012-1-19
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
}
